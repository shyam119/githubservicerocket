import requests
import json
import sys
import sqlite3
from sqlite3 import Error as Err

def fetchGitHubCommitsByRepoAndOwner(owner,repo,per_page,page):
    conn = sqlite3.connect('githubcommit.db')
    cur = conn.cursor()
    tb_create = '''CREATE TABLE github_commit (com_msg text,com_dt text)'''
    tb_exists = "SELECT name FROM sqlite_master WHERE type='table' AND name='github_commit'"
    #condition to check table is already present or not
    if not conn.execute(tb_exists).fetchone():
        #create a table
        conn.execute(tb_create)
        conn.commit()
    url = "https://api.github.com/repos/"+owner.strip()+"/"+repo.strip()+"/commits?per_page="+per_page+"&page="+page
    headers = {
        "Accept" : "application/vnd.github.v3+json"
    }
    response = requests.get(url,headers).json()
    for commitObj in response:
        print(commitObj["commit"]["message"])
        cur.execute("insert into github_commit values (?, ?)", (commitObj["commit"]["message"],
        commitObj["commit"]["committer"]["date"]))
    conn.commit()
    cur.execute('select Count() from github_commit')
    totalCount = cur.fetchone()[0]
    print("TotColCnt:"+str(totalCount))
    conn.close()

owner = sys.argv[1]
repo = sys.argv[2]
per_page = sys.argv[3]
page = sys.argv[4]
fetchGitHubCommitsByRepoAndOwner(owner,repo,per_page,page)
