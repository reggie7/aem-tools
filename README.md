# aem-tools
'AEM Tools' project for the tools AEM lacks (or lacked when I needed them or I didn't know/couldn't find them) that I use often in the projects I take part in.

Developed for and tested on AEM 6.3

## Modules

The main parts of the template are:

* core: Java bundle containing all core functionality like OSGi services, listeners or schedulers, as well as component-related Java code such as servlets or request filters.
* ui.apps: contains the /apps (and /etc) parts of the project, ie JS&CSS clientlibs, components, templates, runmode specific configs as well as Hobbes-tests
* ui.content: contains sample content using the components from the ui.apps

## How to build

To build all the modules run in the project root directory the following command with Maven 3:

	mvn clean install

If you have a running AEM instance you can build and package the whole project and deploy into AEM with  

	mvn clean install -PautoInstallPackage

Or to deploy it to a publish instance, run

	mvn clean install -PautoInstallPackagePublish

Or alternatively

	mvn clean install -PautoInstallPackage -Daem.port=4503

Or to deploy only the bundle to the author, run

	mvn clean install -PautoInstallBundle

## Testing

There is one level of testing contained in the project:

* unit test in core: this show-cases classic unit testing of the code contained in the bundle. To test, execute:

	mvn clean test


## Maven settings

The project comes with the auto-public repository configured. To setup the repository in your Maven settings, refer to:

http://helpx.adobe.com/experience-manager/kb/SetUpTheAdobeMavenRepository.html


## Author

Radosław Wesołowski:
* [Linked In](https://www.linkedin.com/in/wesolowskiradoslaw/)
* [reggie-7.pl](http://reggie-7.pl/)
* [GitHub](https://github.com/reggie7)
* [Stack Overflow](https://stackoverflow.com/users/4773331/rados%C5%82aw-weso%C5%82owski)
* [Enigma Solutions](http://enigmatic.pl/)