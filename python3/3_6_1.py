import json
import urllib.request, urllib.parse, urllib.error

data = '''
[
  { "id" : "001",
    "x" : "2",
    "name" : "Chuck"
  } ,
  { "id" : "009",
    "x" : "7",
    "name" : "Brent"
  }
]'''

address = input('Enter location: ')
url = address
print('Retrieving', url)
uh = urllib.request.urlopen(url)
data = uh.read()

info = json.loads(data)
print('User count:', len(info['comments']))


cnt = 0
sums = 0
for item in info['comments']:
    cnt = cnt + 1
    sums = sums + int(item['count'])
print(cnt)
print(sums)
