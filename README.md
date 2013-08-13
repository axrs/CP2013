CP2013
======

Software Engineering - Semester 2 - 2013

# SERVER
## Installation
### OSX and UNIX Variants
This assumes some knowledge of the UNIX environment and by extension the shell.

1. Open the CLI.

2. Create a directory to download the node source:
   `mkdir nodeSrc`

3. Fetch the latest source code from nodejs.org:
   `wget http://nodejs.org/dist/v0.10.15/node-v0.10.15.tar.gz`

4. Extract the source file. `tar -zxvf node*.tar.gz`

5. Move into the extracted directory: `cd node*`

6. Configure the installation: `[sudo] ./configure`

7. Make and install: `[sudo] make && make install`

If all went well, you should be able to check by issuing `node --version` to see the current running version. 

## Fetch required packages

The server relies on a few modules to improve development time.  These modules are publicly available and are fetched in accordance with the `package.json` file.  Ensure you have the required modules each time you checkout the server by:

1. Navigating to the server directory.

2. Deleting the `node_modules` directory then running `npm cache clean` and finally running `npm install`.  The `npm install` will read the `package.json` file and download the required modules and any of the 3rd party module dependencies.  It should be noted that this may take some time (especially as SQLite3 will be compiled from source).

## Starting the server

The server may be started several different ways.

### Using Node directly

Issuing a `node server.js` will start the server application.  This method is the simplest, however will block the current shell.  As such, the current instance may be terminated by pressing `ctrl+c` or the platform equivalent. 

### Using Forever

Forever is a node module used to run any kind of script continuously.  Basically, if the application crashes, the Forever will restart the server for you.  This method is ideal when using in a production environment as you are able to start and stop the application on demand without blocking the shell.

To use this module however, we must first install it.  It can be downloaded by issuing the `npm install forever -g`.  This command installs the module as a global reference and therefore may be used to start any other node applications.

If Forever is installed, issue a `forever start server.js` to start the script and `forever stop server.js` to stop it.

### Using Supervisor

Node-Supervisor is a script which watches the application for code changes and pre-emptively restarts the application.  This is incredibly useful when developing and debugging as you are not required to interact with the shell at all and are able to focus primarily on development.  Just don't save every 10 seconds.

If the module is not currently installed, it can be obtained by issuing `npm install supervisor -g`.

Once the module is globally installed, issue a `supervisor server.js` to start the application and begin watching for changes.
