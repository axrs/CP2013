-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
INSTALLATION
-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
The Server application requires Node.JS and a few additional modules to be
installed prior to running.

Firstly, Node.JS needs to be installed.  Follow the instructions available at
http://nodejs.org/ for installation requirements for your current platform.

After installation, you will need to obtain the additional packages, including:
-Express
-SQLite3
-Socket.IO

These are easily installed by navigating to the application directory and
executing 'npm install' from the command line.  If node is installed, NPM will
automatically fetch and install the required modules.

It should be noted that some of the modules may required additional packages
to be installed on your system (in the case of windows, SQLite3 requires python
2.7).


-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
RUNNING
-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
To start the server, navigate to the application directory through the command
line interface and execute 'node server.js'.  You should see a line similar to:

Server started listening on port xx.

This indicates the server is running and accepting connections on the port xx.