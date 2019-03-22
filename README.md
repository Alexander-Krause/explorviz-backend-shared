<p align="center">
  <img width="60%" src="https://raw.githubusercontent.com/ExplorViz/Docs/master/images/explorviz-logo.png">
</p>

___

[![TravsCI Build Status](https://travis-ci.org/ExplorViz/explorviz-backend-shared.svg?branch=master)](https://travis-ci.org/ExplorViz/explorviz-backend-shared)


This repository holds a Gradle multi-project containing shared source code files for ExplorViz backend services. Each subproject contains files that serve a single purpose, e.g., user model, security matters, or injection of config variables.

You can easily add a subproject to your service by using [Jitpack](https://jitpack.io/). Follow Jitpack's `How to` section and finally add a subproject to your dependencies as shown below, where `SUBPROJECTNAME` has to be replaced with the name of the desired subproject.

```
dependencies {
  ...
  
  // use release 1.3.1
  implementation 'com.github.explorviz.explorviz-backend-shared:SUBPROJECTNAME:v1.3.1'

  // use latest commit of master branch
  // implementation 'com.github.explorviz.explorviz-backend-shared:SUBPROJECTNAME:master-SNAPSHOT'
}

```

### Local Development
If you want to locally develop against this repository, therefore do not wait for Jitpack all the time, see the `settings.gradle` file of the `explorviz-backend` root project. 
