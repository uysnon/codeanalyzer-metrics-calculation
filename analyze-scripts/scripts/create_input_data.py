from os import walk

import requests as requests
import json
import csv

from model import Report
from model import ReportUnit
from model import LabsReport


def createCsvWithBettaAndPassed(directory_with_inputs:list, filename:str):
    def isProjectFile(fileName: str):
        if fileName.endswith('.zip'):
            return True
        else:
            return False

    def createListOfProjectsFromParentDirectory(parentDirectories: list):
        result = []
        for f in parentDirectories:
            for (dirpath, dirnames, filenames) in walk(f):
                for filename in filenames:
                    if isProjectFile(filename):
                        result.append(f'{dirpath}/{filename}')
                break
        return result

    passedNames = [x[0] for x in directory_with_inputs]
    betaNames = [x[1] for x in directory_with_inputs]

    passedProjects = createListOfProjectsFromParentDirectory(passedNames)
    betaProjects = createListOfProjectsFromParentDirectory(betaNames)

    report_units_passed = [sendProjectToCalculator(pathToZip) for pathToZip in passedProjects]
    report_units_beta = [sendProjectToCalculator(pathToZip) for pathToZip in betaProjects]

    labs_report_map = {
        reportUnit.description.lower(): LabsReport(reportUnit.description.lower(), reportUnit.report_units)
        for reportUnit in report_units_passed}
    for reportUnit in report_units_beta:
        try:
            labs_report_map[reportUnit.description.lower()].report_units_beta = reportUnit.report_units
        except KeyError:
            print(f'project has not it passed version: {reportUnit.description}')
    labs_reports = list(labs_report_map.values())
    write_reports_to_csv(labs_reports, filename)


def write_reports_to_csv(lab_reports, filename):
    csv_lines = []
    csv_lines.append(lab_reports[0].get_csv_line_header())
    for lab_report in lab_reports:
        new_lines = lab_report.get_csv_lines_content()
        for line in new_lines:
            csv_lines.append(line)

    with open(filename, 'w', newline='') as file:
        writer = csv.writer(file, delimiter=',')
        for line in csv_lines:
            writer.writerow(line)


def sendProjectsToCalculatorAndGetReport(projects_names, reportFileName):
    report_units = [sendProjectToCalculator(pathToZip) for pathToZip in projects_names]
    lab_report_units = [LabsReport(reportUnit.description.lower(), reportUnit.report_units) for reportUnit in report_units]
    write_reports_to_csv(lab_report_units, reportFileName)

def sendProjectToCalculator(zipPath: str):
    with open(zipPath, 'rb') as f:
        r = requests.post('http://localhost:8100/metrics/analyze/report', files={'file': f})
        j = json.loads(r.content.decode('utf-8'))
        return Report(**j)




base_path = 'D:\\radik\\diplom\\project\\input_data\\labs\\044'
directory_with_inputs_2_lab = [
    [f'{base_path}\\02', f'{base_path}\\02_beta'],
]

directory_with_inputs_3_lab = [
    [f'{base_path}\\03', f'{base_path}\\03_beta'],
]

directory_with_inputs_4_lab = [
    [f'{base_path}\\04', f'{base_path}\\04_beta'],
]

jdk6_path = 'D:\\radik\\diplom\\project\\jdks\\zulu6.22.0.3-jdk6.0.119-win_x64\\src.zip'
jdk7_path = 'D:\\radik\\diplom\\project\\jdks\\zulu7.56.0.11-ca-jdk7.0.352-win_x64\\src.zip'
jdk8_path = 'D:\\radik\\diplom\\project\\jdks\\jdk8u362-b09\\src.zip'
jdk11_path = 'D:\\radik\\diplom\\project\\jdks\\jdk-11.0.18+10\\lib\\src.zip'
jdk13_path = 'D:\\radik\\diplom\\project\\jdks\\zulu13.54.17-ca-jdk13.0.14-win_x64\\lib\\src.zip'
jdk15_path = 'D:\\radik\\diplom\\project\\jdks\\zulu15.46.17-ca-jdk15.0.10-win_x64\\lib\\src.zip'
jdk16_path = 'D:\\radik\\diplom\\project\\jdks\\jdk-16.0.2+7\\lib\\src.zip'
jdk17_path = 'D:\\radik\\diplom\\project\\jdks\\jdk-17.0.6+10\\lib\\src.zip'
jdk19_path = 'D:\\radik\\diplom\\project\\jdks\\jdk-19.0.2+7\\lib\\src.zip'

# directory_with_inputs_3_lab = [
#     ['/Users/avgorkin/radik/vkr/project/data/Java/7413/03', '/Users/avgorkin/radik/vkr/project/data/Java/7413/03/beta'],
#     ['/Users/avgorkin/radik/vkr/project/data/Java/743/03', '/Users/avgorkin/radik/vkr/project/data/Java/743/03/beta']
# ]

# createCsvWithBettaAndPassed(directory_with_inputs_2_lab, '2lab.csv')
# createCsvWithBettaAndPassed(directory_with_inputs_3_lab, '3lab.csv')
# createCsvWithBettaAndPassed(directory_with_inputs_4_lab, '4lab.csv')


# sendProjectsToCalculatorAndGetReport([jdk6_path, jd k7_path, jdk8_path, jdk11_path], 'jdk_full_p1.csv')
# sendProjectsToCalculatorAndGetReport([jdk13_path, jdk15_path, jdk16_path, jdk17_path, jdk19_path], 'jdk_full_p2.csv')
not_passed_work_1 = 'D:\\radik\diplom\\project\\input_data\\labs\\044\\02_beta\\02-02-Arkhipov.zip'
not_passed_work_2 = 'D:\\radik\diplom\\project\\input_data\\labs\\044\\02_beta\\02-15-sattarova.zip'
passed_work_1 = 'D:\\radik\\diplom\\project\\input_data\\labs\\044\\03\\03-19-Eminov.zip'
passed_work2 = 'D:\\radik\\diplom\\project\\input_data\\labs\\044\\03\\03-07-Lanin.zip'


sendProjectsToCalculatorAndGetReport([not_passed_work_1, not_passed_work_2, passed_work_1, passed_work2], 'test_data.csv')


# with open('report.xls', 'rb') as f:
#     r = requests.post('http://httpbin.org/post', files={'file': f})
