# To run this, you can install BeautifulSoup
# https://pypi.python.org/pypi/beautifulsoup4

# Or download the file
# http://www.py4e.com/code3/bs4.zip
# and unzip it in the same directory as this file

import urllib.request, urllib.parse, urllib.error
from bs4 import BeautifulSoup
import ssl
import re

# Ignore SSL certificate errors
ctx = ssl.create_default_context()
ctx.check_hostname = False
ctx.verify_mode = ssl.CERT_NONE

def fetchurl(url,posexp):
    posnow = 0

    html = urllib.request.urlopen(url, context=ctx).read()
    soup = BeautifulSoup(html, 'html.parser')

    # Retrieve all of the anchor tags
    tags = soup('a')
    for tag in tags:
        #print(tag.get('href', None))
        v = tag.get('href', None)
        if v == None:continue;
        posnow = posnow + 1
        if posnow == posexp :
            return v

def main():
    url = input('Enter - ') #http://py4e-data.dr-chuck.net/known_by_Maizy.html
    count = int(input("Enter count:"))
    position = int(input("Enter position:"))

    for i in range(count):
        url = fetchurl(url,position)
    print(re.findall("known_by_(.*)\.html",url))

main()
