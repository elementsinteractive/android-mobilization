#!/bin/bash

create_gradle_properties() {
  KEYID=$1
  PASSWORD=$2
  GPG_KEY_CONTENTS=$3
  NEXUS_USERNAME=$4
  NEXUS_PASSWORD=$5
  GPG_LOCATION=~/.gradle/release.gpg
  GRADLE_PROPERTIES_LOCATION=~/.gradle/gradle.properties

  mkdir -p ~/.gradle

  rm -f $GPG_LOCATION

  echo $GPG_KEY_CONTENTS | base64 -d > $GPG_LOCATION

  printf "\nsigning.keyId=$KEYID\nsigning.password=$PASSWORD\nsigning.secretKeyRingFile=$GPG_LOCATION\n" >> $GRADLE_PROPERTIES_LOCATION
  printf "SONATYPE_NEXUS_USERNAME=$NEXUS_USERNAME\nSONATYPE_NEXUS_PASSWORD=$NEXUS_PASSWORD\n" >> $GRADLE_PROPERTIES_LOCATION
}

create_gradle_properties $ORG_GRADLE_PROJECT_SIGNINGKEYID $ORG_GRADLE_PROJECT_SIGNINGPASSWORD $GPG_KEY_CONTENTS $SONATYPE_NEXUS_USERNAME $SONATYPE_NEXUS_PASSWORD
