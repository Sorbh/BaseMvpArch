# BaseMVP

![open source](https://img.shields.io/badge/Open%20source-free-green.svg?longCache=true&style=flat-square)
![build number](https://img.shields.io/badge/Build-passing-green.svg?longCache=true&style=flat-square)
![version number](https://img.shields.io/badge/Download-1.0.0-blue.svg?longCache=true&style=flat-square)
![Size](https://img.shields.io/badge/Size-9KB-blue.svg?longCache=true&style=flat-square)
![Method count](https://img.shields.io/badge/Method%20count-25k-red.svg?longCache=true&style=flat-square)
![License Apache](https://img.shields.io/badge/License-apache%202.0-red.svg?longCache=true&style=flat-square)


Android implementation of MVP (Model-View-Presenter).

The library contains base classes that you can use to implement an MVP architecture into your application.
It takes care of injecting the Presenter into your views and attaching/detaching the view from the Presenter.
It also has the basic mechanism to save the state of the presenter on orientation change. It saves the presenter in a static hashmap and returns on orientation change.

Everything else is up to you!

# Motivation

During the learning on MVP architecture. I feel the need of something which provide all the basic implementation of MVP architecture, Plus side by side provide helper method for loading, error or content state, handle presenter state on orientation change.


# Getting started

## Installing
To use this library simply import it by placing the following line under dependencies in your app module's build.gradle file

This library is posted in jCenter

#### Gradle
```Gradle
implementation 'in.unicodelabs.sorbh:basemvparch:1.0.0'
```

#### Maven
```Gradle
<dependency>
  <groupId>in.unicodelabs.sorbh</groupId>
  <artifactId>basemvparch</artifactId>
  <version>1.0.0</version>
  <type>pom</type>
</dependency>
```

# Usage

### Description
Each of your screen should be separated into four classes :
* The view contract
* The presenter contract
* The view that extends MvpActivty or MvpFragment and implements the view contract
* The presenter that extends BasePresenter and implements the presenter contract

For a better readability, you can regroup the two interfaces in a common file.

### Example
Below is an example of the three files that you will need for an EventList screen:

* View and presenter Contracts:
```java
interface EventListContract {

    interface View extends MvpContract.View {
        ...
    }

    interface Presenter extends MvpContract.Presenter<View> {
        ...
    }

}
```

* View Class:
```java
public class EventListActivity extends MvpActivity<EventListContract.Presenter> implements EventListContract.View {
   ...
}
```

* Presenter Class:
```java
class EventListPresenter extends BasePresenter<EventListContract.View> implements EventListContract.Presenter {
    ...
}
```


## Sample
The repository contains a basic example on how to use the MVP classes with both an Activity and a Fragment.

## Inspiration
The MVP implementation and the sample architecture are mostly inspired by:

* [The Mosby library](https://github.com/sockeqwe/mosby)


# Author
  * **Saurabh K Sharma - [GIT](https://github.com/Sorbh)**

      I am very new to open source community. All suggestion and improvement are most welcomed.


## Contributing

1. Fork it (<https://github.com/Sorbh/BaseMvpArch/fork>)
2. Create your feature branch (`git checkout -b feature/fooBar`)
3. Commit your changes (`git commit -am 'Add some fooBar'`)
4. Push to the branch (`git push origin feature/fooBar`)
5. Create a new Pull Request


# License

```
Copyright 2018 Saurabh Kumar Sharma

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
