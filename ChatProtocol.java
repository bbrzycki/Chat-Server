/**
 * ChatProtocol constants.
 * <p>
 * A protocol message is constructed as follows:
 * <ul>
 * <li>A header, consisting of
 * <ul>
 * <li>(byte) protocol version number
 * <li>(byte) operation code
 * <li>(int) payload length, not including this field, the version field, or the
 * opcode
 * </ul>
 * <li>optionally, additional payload, specified for each opcode
 * </ul>
 * <p>
 * If the additional payload includes a {@code String}, it will be encoded as
 * follows:
 * <ul>
 * <li>(byte[]) String encoded in the UTF-8 character set
 * 
 * </ul>
 * <p>
 * Because there is no "start session" command in the protocol that will
 * establish a connection to the server, we always send the protocol version
 * number. Note that since there is no login operation, the server is expected
 * to accept any socket connection from any client.
 * <p>
 * The {@code protocol version} will be checked by the server to ensure that the
 * client and server are using the same protocol or versions of the protocol
 * that are compatible. If the server determines that the protocol version used
 * by the client and the protocol version or versions required by the server are
 * not compatible, the server will disconnect the client. In cases where the
 * protocols being used are not compatible, no other communication between the
 * client and the server is guaranteed to be understood.
 * <p>
 * We follow the Java language definitions of the basic data types. In
 * particular, a {@code byte} is defined as 8 bits, and an {@code int} is defined
 * as 4 bytes (32 bits). {@code String} representations will vary from
 * environment to environment, which is why we define UTF-8 as the transfer
 * encoding for strings. Behavior not specified in this document is left as an
 * implementation decision for the particular client and server.
 */
public final class ChatProtocol
{
	/**
	 * The size of the header for each message: {@value #HEADER_SIZE}
	 * bytes.
	 */
	public static final int HEADER_SIZE = 6;

	/**
	 * Size of the Opcode and Version part of the header
	 */
	public static final int CODE_SIZE = 2;

	/** The version number, currently {@code 0x01}. */
	public static final byte VERSION = 0x01;

	// Opcodes

	/**
	 * Account creation request from a client to a server. This message should
	 * only be sent to a server; if received by a client it should be ignored. <br>
	 * Opcode: {@code 0x10} <br>
	 * Payload:
	 * <ul>
	 * <li>{@code (String) account_name}
	 * </ul>
	 * 
	 * <p>
	 * The {@code account_name} is the name of the account the client wishes to create. 
	 * After the server processes the creation request, the server sends one 
	 * of the following acknowledgments to the client:
	 * <ul>
	 * <li>{@link #CREATE_ACCOUNT_SUCCESS}, if account creation succeeds.
	 * <li>{@link #CREATE_ACCOUNT_FAILURE}, if account creation fails.
	 * </ul>
	 */
	public static final byte CREATE_ACCOUNT_REQUEST = 0x10;

	/**
	 * Account creation success. Server response to a client's
	 * {@link #CREATE_ACCOUNT_REQUEST}. <br>
	 * Opcode: {@code 0x11} <br>
	 * Payload:
	 * <ul>
	 * <li>{@code (String) account_name}
	 * </ul>
	 * The {@code account_name} is the name of the account the server just
	 * created.
	 */
	public static final byte CREATE_ACCOUNT_SUCCESS = 0x11;

	/**
	 * Account creation failure. Server response to a client's
	 * {@link #CREATE_ACCOUNT_REQUEST}. <br>
	 * Opcode: {@code 0x12} <br>
	 * Payload:
	 * <ul>
	 * <li>{@code (String) reason}
	 * </ul>
	 * This message indicates that the server rejects the
	 * {@link #CREATE_ACCOUNT_REQUEST} for some reason, for example:
	 * <ul>
	 * <li>invalid desired account name
	 * <li>unavailable desired account name
	 * </ul>
	 */
	public static final byte CREATE_ACCOUNT_FAILURE = 0x12;

	/**
	 * Login request from a client to a server. This message should
	 * only be sent to a server; if received by a client it should be ignored. <br>
	 * Opcode: {@code 0x13} <br>
	 * Payload:
	 * <ul>
	 * <li>{@code (String) account_name}
	 * </ul>
	 * 
	 * <p>
	 * The {@code account_name} is the name of the account the client wishes to login to. 
	 * After the server processes the login request, the server sends one 
	 * of the following acknowledgments to the client:
	 * <ul>
	 * <li>{@link #LOGIN_SUCCESS}, if login succeeds.
	 * <li>{@link #LOGIN_FAILURE}, if login fails.
	 * </ul>
	 */
	public static final byte LOGIN_REQUEST = 0x13;

	/**
	 * Login success. Server response to a client's
	 * {@link #LOGIN_REQUEST}. <br>
	 * Opcode: {@code 0x14} <br>
	 * Payload:
	 * <ul>
	 * <li>{@code (String) account_name}
	 * <li>{@code (boolean) unreadMessages}
	 * </ul>
	 * The {@code account_name} is the name of the account the client has
	 * logged into. The {@code unreadMessages} passes along to the client
	 * whether the client has unread messages in the inbox.
	 */
	public static final byte LOGIN_SUCCESS = 0x14;

	/**
	 * Login failure. Server response to a client's
	 * {@link #LOGIN_REQUEST}. <br>
	 * Opcode: {@code 0x15} <br>
	 * Payload:
	 * <ul>
	 * <li>{@code (String) reason}
	 * </ul>
	 * This message indicates that the server rejects the
	 * {@link #LOGIN_REQUEST} for some reason, for example:
	 * <ul>
	 * <li>invalid account name
	 * </ul>
	 */
	public static final byte LOGIN_FAILURE = 0x15;



	/**
	 * Account destruction request from a client to a server. This message
	 * should only be sent to a server; if received by a client it should be
	 * ignored. <br>
	 * Opcode: {@code 0x20} <br>
	 * Payload:
	 * <ul>
	 * <li>{@code (String) account_name}
	 * </ul>
	 * 
	 * <p>
	 * The {@code account_name} is the name of the account to be deleted.
	 * After the server processes the deletion request, the server sends one of
	 * the following acknowledgments to the client:
	 * <ul>
	 * <li> {@link #DELETE_ACCOUNT_SUCCESS}, if account deletion succeeds.
	 * <li>{@link #DELETE_ACCOUNT_FAILURE}, if account deletion fails.
	 * </ul>
	 */
	public static final byte DELETE_ACCOUNT_REQUEST = 0x20;

	/**
	 * Account deletion success. Server response to a client's
	 * {@link #DELETE_ACCOUNT_REQUEST}. <br>
	 * Opcode: {@code 0x21} <br>
	 * Payload: None.
	 */
	public static final byte DELETE_ACCOUNT_SUCCESS = 0x21;

	/**
	 * Account deletion failure. Server response to a client's
	 * {@link #DELETE_ACCOUNT_REQUEST}. <br>
	 * Opcode: {@code 0x22} <br>
	 * Payload:
	 * <ul>
	 * <li>{@code (String) reason}
	 * </ul>
	 * This message indicates that the server rejects the
	 * {@link #DELETE_ACCOUNT_REQUEST} for some reason, for example:
	 * <ul>
	 * <li>invalid account name
	 * </ul>
	 */
	public static final byte DELETE_ACCOUNT_FAILURE = 0x22;

	/**
	 * List all accounts request from a client to a server. Requests a list of
	 * all accounts, or a subset of accounts by text wildcard. The server should
	 * then begin to send all account names, one per protocol message, to the client.
	 * This message should only be sent to a server; if received by a client it 
	 * should be ignored. <br>
	 * Opcode: {@code 0x30} <br>
	 * Payload:
	 * <ul>
	 * <li>{@code (String) regular_expression}
	 * </ul>
	 * 
	 * <p>
	 * The {@code regular_expression} is a Java regular expression used to pattern
	 * match account names. Can be '.*' to obtain all accounts currently saved.
	 * After the server processes the list all accounts request, the
	 * server sends one of the following acknowledgments to the client:
	 * <ul>
	 * <li>{@link #LIST_ALL_ACCOUNTS_SUCCESS}, if list all accounts request succeeds.
	 * <li>{@link #LIST_ALL_ACCOUNTS_FAILURE}, if list all accounts request fails.
	 * </ul>
	 */
	public static final byte LIST_ALL_ACCOUNTS_REQUEST = 0x30;

	/**
	 * List all accounts success. Server response to a client's
	 * {@link #LIST_ALL_ACCOUNTS_REQUEST}. <br>
	 * Opcode: {@code 0x31} <br>
	 * Payload: None.
	 */
	public static final byte LIST_ALL_ACCOUNTS_SUCCESS = 0x31;

	/**
	 * List all accounts failure. Server response to a client's 
	 * {@link #LIST_ALL_ACCOUNTS_REQUEST}. <br>
	 * Opcode: {@code 0x32} <br>
	 * Payload:
	 * <ul>
	 * <li>{@code (String) reason}
	 * </ul>
	 * This message indicates that the server rejects the
	 * {@link #LIST_ALL_ACCOUNTS_REQUEST} for some reason, for example:
	 * <ul>
	 * <li>invalid regular expression.
	 * </ul>
	 */
	public static final byte LIST_ALL_ACCOUNTS_FAILURE = 0x32;
	
	/**
	 * Send message request from a client to a server. Requests a message String
	 * to be sent to a specific user. This message should only be sent to a server;
	 * if received by a client it should be ignored. <br>
	 * Opcode: {@code 0x40} <br>
	 * Payload:
	 * <ul>
	 * <li>{@code (String) sender_name}
	 * <li>{@code (String) receiver_name}
	 * <li>{@code (String) message}
	 * </ul>
	 * 
	 * <p>
	 * The {@code receiver_name} is the name of the account receiving the message
	 * {@code message}. The {@code (String) sender_name} is the account sending the message.
	 * After the server processes the send message request, the
	 * server sends one of the following acknowledgments to the client:
	 * <ul>
	 * <li>{@link #SEND_MESSAGE_SUCCESS}, if send message request succeeds.
	 * <li>{@link #SEND_MESSAGE_FAILURE}, if send message request fails.
	 * </ul>
	 */
	public static final byte SEND_MESSAGE_REQUEST = 0x40;

	/**
	 * Send message success. Server response to a client's
	 * {@link #SEND_MESSAGE_REQUEST}. <br>
	 * Opcode: {@code 0x41} <br>
	 * Payload: None.
	 */
	public static final byte SEND_MESSAGE_SUCCESS = 0x41;

	/**
	 * Send message failure. Server response to a client's 
	 * {@link #SEND_MESSAGE_REQUEST}. <br>
	 * Opcode: {@code 0x42} <br>
	 * Payload:
	 * <ul>
	 * <li>{@code (String) reason}
	 * </ul>
	 * This message indicates that the server rejects the
	 * {@link #SEND_MESSAGE_REQUEST} for some reason, for example:
	 * <ul>
	 * <li>invalid receiver account name
	 * <li>invalid message
	 * <li>message exceeds maximum length
	 * </ul>
	 */
	public static final byte SEND_MESSAGE_FAILURE = 0x42;

	/**
	 * Pull all messages request from a client to a server. Requests all messages 
	 * under the client's account, to be displayed to the client. The server should
	 * send all messages to the client.
	 * This message should only be sent to a server;
	 * if received by a client it should be ignored. <br>
	 * Opcode: {@code 0x50} <br>
	 * Payload: None.
	 * 
	 * <p>
	 * After the server processes the pull all messages request, the
	 * server sends one of the following acknowledgments to the client:
	 * <ul>
	 * <li>{@link #PULL_ALL_MESSAGES_SUCCESS}, if pull all messages request succeeds.
	 * <li>{@link #PULL_ALL_MESSAGES_FAILURE}, if pull all messages request fails.
	 * </ul>
	 */
	public static final byte PULL_ALL_MESSAGES_REQUEST = 0x50;

	/**
	 * Pull all messages success. Server response to a client's
	 * {@link #PULL_ALL_MESSAGES_REQUEST}. <br>
	 * Opcode: {@code 0x51} <br>
	 * Payload: None.
	 */
	public static final byte PULL_ALL_MESSAGES_SUCCESS = 0x51;

	/**
	 * Pull all messages failure. Server response to a client's 
	 * {@link #PULL_ALL_MESSAGES_REQUEST}. <br>
	 * Opcode: {@code 0x52} <br>
	 * Payload:
	 * <ul>
	 * <li>{@code (String) reason}
	 * </ul>
	 * This message indicates that the server rejects the
	 * {@link #PULL_ALL_MESSAGES_REQUEST} for some reason.
	 */
	public static final byte PULL_ALL_MESSAGES_FAILURE = 0x52;

	/**
	 * Server pushes notification that client has received a message
	 * {@link #PUSH_MESSAGE_NOTIFICATION}. <br>
	 * Opcode: {@code 0x53} <br>
	 * No payload.
	 */
	public static final byte PUSH_MESSAGE_NOTIFICATION = 0x53;

	/**
	 * Request from a client to a server to disconnect and log out.
	 * This message should only be sent to a server; if received by a client it
	 * should be ignored. <br>
	 * Opcode: {@code 0x60} <br>
	 * Payload: None.
	 * 
	 * <p>
	 * This operation cannot fail, as it simply notifies the server of the client's
	 * intent to log out. After the server processes the disconnect request,
	 * the server sends {@link #END_SESSION_SUCCESS} to the client.
	 */
	public static final byte END_SESSION_REQUEST = 0x60;

	/**
	 * End session success. Server response to a client's {@link #END_SESSION_REQUEST}. <br>
	 * Opcode: {@code 0x61} <br>
	 * Payload: None.
	 */
	public static final byte END_SESSION_SUCCESS = 0x61;

	/**
	 * Heartbeat opcode to check if client is connected. <br>
	 * Opcode: {@code 0x62} <br>
	 * Payload: None.
	 */
	public static final byte HEARTBEAT = 0x62;

	/**
	 * Message from the server to the client indicating that a message with an
	 * unknown opcode has been received. On the second receipt of an unknown
	 * opcode, the server will disconnect the client, after sending an
	 * {@link #END_SESSION_SUCCESS} message. Opcode: {@code Ox70}<br>
	 * Payload: None.
	 */
	public static final byte UNKNOWN_OPCODE = 0x70;
}
