#!/bin/bash

# exit when any command fails
set -e

# keep track of the last executed command
trap 'last_command=$current_command; current_command=$BASH_COMMAND' DEBUG
# echo an error message before exiting
trap 'echo "KERUBIN FINISHED: last command \"${last_command}\" had exit code $?."' EXIT

echo "Starting build generation..."

echo "Packing modules..."
mvn clean install

echo "DONE"

#read -p "Press any key to exit..."