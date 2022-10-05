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

    def sendProjectToCalculator(zipPath: str):
        with open(zipPath, 'rb') as f:
            r = requests.post('http://localhost:8100/metrics/analyze/report', files={'file': f})
            j = json.loads(r.content.decode('utf-8'))
            return Report(**j)

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

    csv_lines = []
    csv_lines.append(labs_reports[0].get_csv_line_header())
    for lab_report in labs_reports:
        new_lines = lab_report.get_csv_lines_content()
        for line in new_lines:
            csv_lines.append(line)

    with open(filename, 'w') as file:
        writer = csv.writer(file, delimiter=',')
        for line in csv_lines:
            writer.writerow(line)

directory_with_inputs_2_lab = [
    ['/Users/avgorkin/radik/vkr/project/data/Java/7413/02', '/Users/avgorkin/radik/vkr/project/data/Java/7413/02/beta'],
    ['/Users/avgorkin/radik/vkr/project/data/Java/743/02', '/Users/avgorkin/radik/vkr/project/data/Java/743/02/beta'],
]

directory_with_inputs_3_lab = [
    ['/Users/avgorkin/radik/vkr/project/data/Java/7413/03', '/Users/avgorkin/radik/vkr/project/data/Java/7413/03/beta'],
    ['/Users/avgorkin/radik/vkr/project/data/Java/743/03', '/Users/avgorkin/radik/vkr/project/data/Java/743/03/beta']
]

createCsvWithBettaAndPassed(directory_with_inputs_2_lab, '2lab.csv')
createCsvWithBettaAndPassed(directory_with_inputs_3_lab, '3lab.csv')


# with open('report.xls', 'rb') as f:
#     r = requests.post('http://httpbin.org/post', files={'file': f})
