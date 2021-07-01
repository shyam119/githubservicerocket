# githubservicerocket
service rocket take-home exercise to create python script which pulls GitHub commits and insert them
into the database.

Prerequisites:
Java8, Python3, Gradle.

Running in Local :

1. git clone **url** **foldername**
2. To build the project please run the command **./gradlew clean build**
3. To run the project please run the command **./gradlew bootRun**
4. After isntance is up , please access the below end point to trigger python script.
    http://localhost:8090/servicerocket/commits?owner=virejdasani&repo=Commited&per_page=100&page=1
5. when you hit above endpoint , python script will run **GitHubFetchScript.py**
6. Pyhton script will fetch github commits and insert into sqlite3 database.
7. To trigger python script, **owner** and **repo** are the manadatory fields for above end point.
8. After triggering the endpoint, User will be able to know the number of rows in the databse and some interseting commits messages contains **README** text.
   Below is the sample response :
    {
        "totalRowCount": "900",
        "interestingComments": [
        "Update README.md",
        "Update README.md",
        "Update README.md",
        "Update README.md",
        "Update README.md",
        "Update README.md"
        ],
        "errorMessage": null
  }
