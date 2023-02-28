#!/Users/avgorkin/radik/vkr/project/metrics/analyze-scripts/venv/bin/python3

import requests
city_id = 500096
appid = "0ec0c7a5970778fd883b3010950d8a0d"

print('Прогноз погоды в Рязани на 5 дней: ')
try:
    res = requests.get("http://api.openweathermap.org/data/2.5/forecast",
                       params={'id': city_id, 'units': 'metric', 'lang': 'ru', 'APPID': appid})
    data = res.json()
    for i in data['list']:
        print( i['dt_txt'], '{0:+3.0f}'.format(i['main']['temp']), i['weather'][0]['description'] )
except Exception as e:
    print("Exception (forecast):", e)
    pass
