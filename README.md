# Chat-Server

## A simple command-line chat application
`Chat-Server` is a simple chat application with a command-line user interface. It implements a host server and handles multiple users, who may:
* set up an account,
* log in,
* send messages to other users,
* check their messages,
* list other users in the system,
* and delete their accounts.

---

## Installation and Setup

### Set up and initialize server
The Chat-Server server can be hosted on any machine, including your own computer!

First, clone the code repository to the server machine by executing: 
```
git clone git@github.com:bbrzycki/Chat-Server.git
```

All of the important source files are located in the home directory of `Chat-Server`. HTML documentation is located within `javadoc-documentation`. To view complete documentation in a web browser, open `index.html`!

To compile source files, execute:
```
javac *.java
```
> Make sure you have [Java JDK](http://www.oracle.com/technetwork/java/javase/downloads/index.html) installed on the machine! This is necessary for compilation.

Once the source files have been compiled, executing:
```
java Server [port #]
```
will open up the Client-Server server to listen on the `[port #]` specified.

### Set up and initialize client
First, clone the code repository to the client machine by again executing: 
```
git clone git@github.com:bbrzycki/Chat-Server.git
```

To compile source files, execute:
```
javac *.java
```

To initialize a client and begin using the Chat-Server application, execute:
```
java Client [server_ip_address] [port #]
```
The application will then connect to `[server_ip_address]` on `[port #]`, as long as the server is running successfully.

---

## Using Chat-Server
### Log In Menu

On the client-side, users are immediately greeted with a login menu. 
```
--- Log In Menu ---
(1) Create Account
(2) Log In
```
#### Create Account
To create a new account, choose option (1) by typing 1 and pressing enter. Then, enter your chosen username. This must be unique and longer than one correct.

#### Log In
To log into an existing account, select option (2) by typing 2 and pressing enter. The account you wish to log into must exist and not already be logged in.

### User Menu
On successful account creation or login as user `[username]`, users will see another menu:
```
--- [username]'s User Menu ---
(1) Delete Account
(2) Send Message
(3) Check Inbox
(4) List Users
(5) End Session
```
#### Delete Account
This option attempts to delete the user account. If the user currently has unread messages, the application will ask for confirmation; if the user does not confirm, the account will not be deleted.

#### Send Message
This option allows the user to send a message to any other user on the server, whether the other user is logged in or not. Users specify the recipient and the message, which is then delivered immediately to the recipient's inbox. If the recipient is also logged in, they will see a pop-up message that they have received a new message.

#### Check Inbox
This option lets the user check the inbox for received messages, listing all the messages by date.

#### List Users
This option allows the user to either list all users on the server, or a subset of them via a [Java regular expression](https://www.tutorialspoint.com/java/java_regular_expressions.htm) (regex). 

#### End Session
This option simply closes the client connection.
