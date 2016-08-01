FILE=~/Downloads/json-20160212.jar
groupId=org.json
artifactId=json
version=20160212

mvn install:install-file -Dfile=$FILE -DgroupId=$groupId -DartifactId=$artifactId -Dversion=$version -Dpackaging=jar
