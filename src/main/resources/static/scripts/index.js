let socket = null;

   
    function decodeJwt(token) {
        try {
            return JSON.parse(atob(token.split('.')[1]));
        } catch (e) { return {}; }
    }

   
    const username = localStorage.getItem("username");
    const role = localStorage.getItem("role");
    const payload = decodeJwt(localStorage.getItem('jwt'));
    const email = payload.sub || '';
    if (username) {
        document.getElementById("userName").textContent = username;
        document.getElementById("role").textContent = role || '';
        document.getElementById("email").textContent = email;
       
        if (role && role.toLowerCase() === 'admin') {
            document.getElementById("admin-only").textContent = "Admin Panel";
            document.getElementById("admin-only").style.display = "block";
        }
    }

    function logout() {
        localStorage.clear();
        window.location.href = "/login";
    }
	function openPopUp(){
		popup.style.display = "flex";
	}
	
	function closePopup(){
		popup.style.display = "none";
	}
	
	
	async function createGroup() {
	  try {
	    const groupname = document.getElementById("groupName").value.trim();
	    const emails = document.getElementById("memberEmails").value.trim().split(",");

	    if (!groupname || emails.length < 2) {
	      alert("Enter a group name and at least 2 members!");
	      return;
	    }

	    const members = {};

	   
	    for (const email1 of emails) {
	      const getUser = await fetch(`/getUser?email=${email1.trim()}`, {
	        method: 'GET',
	        headers: {
	          "Authorization": "Bearer " + localStorage.getItem("jwt"),
	          "Content-Type": "application/json",
	        }
	      });

	      if (!getUser.ok) {
	        alert(`User with email: ${email1} does not exist`);
	        return;
	      }

	      const user_name = await getUser.json();
		  console.log(user_name);
	      members[email1.trim()] = user_name.username; 
	    }

	  
	    
	    const currentUsername = localStorage.getItem("username");
	   
	      members[email] = currentUsername;
	    

	    const bodyData = { name: groupname, members };

	    const response = await fetch("/chatRooms/joinRoom", {
	      method: 'POST',
	      headers: {
	        "Authorization": "Bearer " + localStorage.getItem("jwt"),
	        "Content-Type": "application/json",
	      },
	      body: JSON.stringify(bodyData),
	    });

	    if (!response.ok) {
	      throw new Error("Error: " + response.status + " " + response.statusText);
	    }

	    const chatRoom = await response.json();
		const chat_rooms = document.getElementById("other-users-and-groups");
		chat_rooms.innerHTML = "";
		
	    loadMessages(chatRoom.id);
	    initSocket(chatRoom.id);
		chat_rooms.appendChild(div);

	  } catch (err) {
	    console.error("Error " + err.message);
	  }
	}



    async function getAll() {
        try {
            const response = await fetch("/users", {
                method: 'GET',
                headers: {
                    "Authorization": "Bearer " + localStorage.getItem("jwt"),
                    "Content-Type": "application/json",
                }
            });
            if (!response.ok) {
                if (response.status === 401) window.location.href = "/login";
                throw new Error("Error: " + response.status + " " + response.statusText);
            }
            const users = await response.json();
            const chat_rooms = document.getElementById("other-users-and-groups");
            chat_rooms.innerHTML = "";
            users.forEach(user => {
                if (user.username === username) return; 
                const div = document.createElement("div");
                div.textContent = user.username;
                div.id = user.username;
                div.tabIndex = 0;
                div.setAttribute("role", "button");
                div.setAttribute("aria-label", `Start chat with ${user.username}`);
                div.addEventListener("click", () => startChat(user));
                div.addEventListener("keypress", e => { if (e.key === 'Enter') startChat(user); });
                chat_rooms.appendChild(div);
            });
        } catch (err) {
            console.error("Error " + err.message);
        }
    }

  
    async function startChat(user) {
       
        document.getElementById("chat-area").style.display = "flex";
        document.getElementById("chat-header").textContent = `Chat with ${user.username}`;
    
        const members = {
			[user.email]: user.username,
			[email]:username
		};
		
        const bodyData = { name: user.username, members };
        try {
            const response = await fetch("/chatRooms/joinRoom", {
                method: 'POST',
                headers: {
                    "Authorization": "Bearer " + localStorage.getItem("jwt"),
                    "Content-Type": "application/json",
                },
                body: JSON.stringify(bodyData),
            });
            if (!response.ok) {
                throw new Error("Error: " + response.status + " " + response.statusText);
            }
            const chatRoom = await response.json();
            loadMessages(chatRoom.id);
            initSocket(chatRoom.id);
        } catch (err) {
            alert("Error starting chat: " + err.message);
        }
    }

    async function loadMessages(chatRoom_id) {
        try {
            const response = await fetch(`/api/messages?roomId=${chatRoom_id}`, {
                method: 'GET',
                headers: {
                    "Authorization": "Bearer " + localStorage.getItem("jwt"),
                    "Content-Type": "application/json",
                },
            });
            if (!response.ok) {
                throw new Error("Error:" + response.status + " " + response.statusText);
            }
            const messages = await response.json();
            
            const chat_messages = document.getElementById("chat-messages");
            chat_messages.innerHTML = ""; // Clear previous messages
            messages.forEach(message => {
                const div = document.createElement("div");
                const h5 = document.createElement("h5");
                h5.textContent = message.sender;
                const p = document.createElement("p");
                p.textContent = message.content;
                div.appendChild(h5);
                div.appendChild(p);
                chat_messages.appendChild(div);
            });
        } catch (err) {
            alert("Couldn't load chats: " + err.message);
        }
    }
    

    function initSocket(chatRoom_id) {
        if (window.stompClient && window.stompClient.connected) {
            console.log("Disconnecting old STOMP client before opening new one");
            window.stompClient.disconnect(() => {
                console.log("Disconnected old STOMP client");
            });
        }

        const token = localStorage.getItem("jwt");
       
        const socket = new SockJS("http://localhost:8080/ws-chat");
        const stompClient = Stomp.over(socket);

        window.stompClient = stompClient; 

        stompClient.connect(
            { Authorization: `Bearer ${token}` }, 
            function (frame) {
                console.log("Connected to STOMP for room " + chatRoom_id);

             
                stompClient.subscribe(`/topic/room.${chatRoom_id}`, function (message) {
                    const msg = JSON.parse(message.body);
                    displayMessage(msg.sender, msg.content);
                });

            
                document.getElementById("send-btn").onclick = () => {
                    const input = document.getElementById("chat-message-input");
                    const msg = {
                        sender: username,
                        content: input.value,
                        roomId: chatRoom_id
                    };
                    stompClient.send("/app/chat.sendMessage", {}, JSON.stringify(msg));
                    input.value = "";
                };
            },
            function (error) {
                console.error("STOMP connection error:", error);
            }
        );
    }

    function displayMessage(sender, content) {
        const chat_messages = document.getElementById("chat-messages"); 
        const div = document.createElement("div");
        const h5 = document.createElement("h5");
        h5.textContent = sender;
        const p = document.createElement("p");
        p.textContent = content;
        div.appendChild(h5);
        div.appendChild(p);
        chat_messages.appendChild(div);
    }

    getAll();