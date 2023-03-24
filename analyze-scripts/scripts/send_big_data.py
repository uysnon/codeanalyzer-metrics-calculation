import requests as requests
import json
import csv

import os


from model import Report
from model import ReportUnit
from model import LabsReport


path_temp = os.environ['PATH_TO_TEMP_ANALYZE']
path_input = os.environ['PATH_TO_INPUT_FILE']
calculator_url = 'http://localhost:8100/metrics/analyze/report'


print(path_temp)
print(path_input)

def sendProjectToCalculator(zipPath: str):
    with open(zipPath, 'rb') as f:
        r = requests.post(calculator_url, files={'file': f})
        j = json.loads(r.content.decode('utf-8'))
        return Report(**j)

