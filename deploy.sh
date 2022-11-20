#!/bin/bash

source ~/.bash_profile
set -e
MAVEN_LOCATION=${1:-$(which mvn)}
bash "${MAVEN_LOCATION}" clean install -P autoInstallPackage -P autoInstallContent
bash "${MAVEN_LOCATION}" clean install -P autoInstallPackagePublish -P autoInstallContentPublish
