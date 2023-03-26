import json
import os
from git import Repo
from git import GitCommandError
import pandas as pd
import re
import shutil
import stat, errno

import requests as requests

from model import Report

path_temp = os.environ['PATH_TO_TEMP_ANALYZE']
path_input = os.environ['PATH_TO_INPUT_FILE']
calculator_url = 'http://localhost:8100/metrics/analyze/report'


def sendProjectToCalculator(zipPath: str):
    with open(zipPath, 'rb') as f:
        r = requests.post(calculator_url, files={'file': f})
        j = json.loads(r.content.decode('utf-8'))
        return Report(**j)


def read_input_csv():
    data = pd.read_csv(path_input)
    return data


def handleRemoveReadonly(func, path, exc):
  excvalue = exc[1]
  if func in (os.rmdir, os.remove) and excvalue.errno == errno.EACCES:
      os.chmod(path, stat.S_IRWXU| stat.S_IRWXG| stat.S_IRWXO) # 0777
      func(path)
  else:
      raise

def on_rm_error(func, path, exc_info):
    #from: https://stackoverflow.com/questions/4829043/how-to-remove-read-only-attrib-directory-with-python-in-windows
    os.chmod(path, stat.S_IWRITE)
    os.unlink(path)
def remove_content_from_dir(dir):
    for filename in os.listdir(dir):
        file_path = os.path.join(dir, filename)
        try:
            if os.path.isfile(file_path) or os.path.islink(file_path):
                os.unlink(file_path)
            elif os.path.isdir(file_path):
                shutil.rmtree(file_path, ignore_errors=False, onerror=on_rm_error)
        except Exception as e:
            print('Failed to delete %s. Reason: %s' % (file_path, e))


def main():
    print(path_temp)
    print(path_input)
    data = read_input_csv()

    git_url_a = []
    score_a = []
    loc_a = []
    avg_method_length_a = []
    adtp = []
    mdtd = []
    cmc = []
    ml15 = []
    mm15 = []
    mm30 = []
    mm60 = []
    cc0_999 = []
    cc11_999 = []
    cc21_999 = []
    cc41_999 = []
    abc_0_10 = []
    abc_11_999 = []
    abc_21_999 = []
    abc_41_999 = []
    abc_61_999 = []
    abc_101_999 = []
    number_a = []

    for index, row in data.iterrows():
        try:
            git_url = row['repository_link']
            score = row['score']
            number = row['#']
            print(f'iteration : {number}, url: {git_url}')
            title_search = re.search('.*\/(.*)', git_url, re.IGNORECASE)
            title = title_search.group(1)
            path_to_temp_project_dir = os.path.join(path_temp, title)
            path_to_temp_project_zip = path_to_temp_project_dir + '.zip'
            Repo.clone_from(git_url, path_to_temp_project_dir)
            shutil.make_archive(path_to_temp_project_dir, 'zip', path_to_temp_project_dir)
            if os.path.getsize(path_to_temp_project_zip) < 1e+8:
                report = sendProjectToCalculator(path_to_temp_project_zip)

                number_a.append(number)
                git_url_a.append(git_url)
                score_a.append(score)
                loc_a.append(report.report_units_dict['LOC']['value'])
                avg_method_length_a.append(report.report_units_dict['AVG_METHOD_LENGTH']['value'])
                adtp.append(report.report_units_dict['AVG_DEPENDENCY_TREE_DEPTH']['value'])
                mdtd.append(report.report_units_dict['MAX_DEPENDENCY_TREE_DEPTH']['value'])
                cmc.append(report.report_units_dict['CODE_SMELLS_COUNT']['value'])
                ml15.append(report.report_units_dict['PERCENTAGE_VOLUME_METHODS_WITH_LENGTH_LESS_THAN_15']['value'])
                mm15.append(report.report_units_dict['PERCENTAGE_VOLUME_METHODS_WITH_LENGTH_MORE_THAN_15']['value'])
                mm30.append(report.report_units_dict['PERCENTAGE_VOLUME_METHODS_WITH_LENGTH_MORE_THAN_30']['value'])
                mm60.append(report.report_units_dict['PERCENTAGE_VOLUME_METHODS_WITH_LENGTH_MORE_THAN_60']['value'])
                cc0_999.append(report.report_units_dict['PERCENTAGE_METHODS_WITH_CYCL_COMPL_IN_RANGE_0_999']['value'])
                cc11_999.append(report.report_units_dict['PERCENTAGE_METHODS_WITH_CYCL_COMPL_IN_RANGE_11_999']['value'])
                cc21_999.append(report.report_units_dict['PERCENTAGE_METHODS_WITH_CYCL_COMPL_IN_RANGE_21_999']['value'])
                cc41_999.append(report.report_units_dict['PERCENTAGE_METHODS_WITH_CYCL_COMPL_IN_RANGE_41_999']['value'])
                abc_0_10.append(report.report_units_dict['PERCENTAGE_METHODS_WITH_ABC_IN_RANGE_0_10']['value'])
                abc_11_999.append(report.report_units_dict['PERCENTAGE_METHODS_WITH_ABC_IN_RANGE_11_999']['value'])
                abc_21_999.append(report.report_units_dict['PERCENTAGE_METHODS_WITH_ABC_IN_RANGE_21_999']['value'])
                abc_41_999.append(report.report_units_dict['PERCENTAGE_METHODS_WITH_ABC_IN_RANGE_41_999']['value'])
                abc_61_999.append(report.report_units_dict['PERCENTAGE_METHODS_WITH_ABC_IN_RANGE_61_999']['value'])
                abc_101_999.append(report.report_units_dict['PERCENTAGE_METHODS_WITH_ABC_IN_RANGE_101_999']['value'])


            remove_content_from_dir(path_temp)
        except GitCommandError as e:
            print(f'git error: {e}')
        except TypeError as e:
            print(f'type error: {e}')

    calculated_data = pd.DataFrame({
        'number' : number_a,
        'GIT_URL': git_url_a,
        'SCORE': score_a,
        'LOC': loc_a,
        'AVG_METHOD_LENGTH': avg_method_length_a,
        'AVG_DEPENDENCY_TREE_DEPTH': avg_method_length_a,
        'MAX_DEPENDENCY_TREE_DEPTH': mdtd,
        'CODE_SMELLS_COUNT': cmc,
        'PERCENTAGE_VOLUME_METHODS_WITH_LENGTH_LESS_THAN_15': ml15,
        'PERCENTAGE_VOLUME_METHODS_WITH_LENGTH_MORE_THAN_15': mm15,
        'PERCENTAGE_VOLUME_METHODS_WITH_LENGTH_MORE_THAN_30': mm30,
        'PERCENTAGE_VOLUME_METHODS_WITH_LENGTH_MORE_THAN_60': mm60,
        'PERCENTAGE_METHODS_WITH_CYCL_COMPL_IN_RANGE_0_999': cc0_999,
        'PERCENTAGE_METHODS_WITH_CYCL_COMPL_IN_RANGE_11_999': cc11_999,
        'PERCENTAGE_METHODS_WITH_CYCL_COMPL_IN_RANGE_21_999': cc21_999,
        'PERCENTAGE_METHODS_WITH_CYCL_COMPL_IN_RANGE_41_999': cc41_999,
        'PERCENTAGE_METHODS_WITH_ABC_IN_RANGE_0_10': abc_0_10,
        'PERCENTAGE_METHODS_WITH_ABC_IN_RANGE_11_999': abc_11_999,
        'PERCENTAGE_METHODS_WITH_ABC_IN_RANGE_21_999': abc_21_999,
        'PERCENTAGE_METHODS_WITH_ABC_IN_RANGE_41_999': abc_41_999,
        'PERCENTAGE_METHODS_WITH_ABC_IN_RANGE_61_999': abc_61_999,
        'PERCENTAGE_METHODS_WITH_ABC_IN_RANGE_101_999': abc_101_999
    })

    calculated_data.to_csv('calculated_55k.csv', sep=',')



if __name__ == "__main__":
    main()
