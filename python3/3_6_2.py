import urllib.request, urllib.parse, urllib.error
import json

# Note that Google is increasingly requiring keys
# for this API
serviceurl = 'http://py4e-data.dr-chuck.net/geojson?'

uh = urllib.request.urlopen(serviceurl)
data = uh.read().decode()
print('Retrieved', len(data), 'characters')
paralist = json.loads(data)

for address in paralist:

    url = serviceurl + urllib.parse.urlencode(
        {'address': address})

    print('Retrieving', url)
    uh = urllib.request.urlopen(url)
    data = uh.read().decode()
    print('Retrieved', len(data), 'characters')

    try:
        js = json.loads(data)
    except:
        js = None

    if not js or 'status' not in js or js['status'] != 'OK':
        print('==== Failure To Retrieve ====')
        continue

    if js['results'][0]['place_id'].startswith('ChIJTy7'):
        print(js['results'][0]['place_id'])
        break
