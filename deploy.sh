#!/bin/bash

if [ -d "$HOME/.sdkman" ]; then
    source "$HOME/.sdkman/bin/sdkman-init.sh"
    sdk version

    all_installed=true
    while IFS='=' read -r candidate version; do
        [[ "$candidate" =~ ^#.*$ || -z "$candidate" ]] && continue
        candidate=$(echo "$candidate" | xargs)
        version=$(echo "$version" | xargs)
        if ! sdk list "$candidate" | grep -qE " $version(\s|\*|\+|$)"; then
            echo "$candidate $version is NOT installed."
            all_installed=false
        else
            echo "$candidate $version is installed."
        fi
    done < .sdkmanrc

    if $all_installed; then
        sdk env
    else
      sdk env install
    fi
fi

./mvnw clean install -P autoInstallPackage -P autoInstallContent "$@"
./mvnw clean install -P autoInstallPackagePublish -P autoInstallContentPublish "$@"
