import React, { useEffect, useState } from "react";

import logo from "./logo.svg";
import "./App.css";

import axios from "axios";

function App() {
  const [isLoggedIn, setLoggedIn] = useState(false);

  useEffect(() => {
    const userId = 1;

    const fetchData = async () => {
      console.log("Subscribing...");
      try {
        await axios.post("http://localhost:8080/api/notifications/subscribe", {
          userId: userId,
        });
        setLoggedIn(true);
        console.log("Subscribed");
      } catch (error) {
        console.error("Error subscribing:", error);
      }
    };

    fetchData();

    if (isLoggedIn) {
      console.log("Event listener...");
      const eventSource = new EventSource(
        "http://localhost:8080/api/notifications/events/1"
      );
      eventSource.onerror = () => {
        fetchData();
      };

      eventSource.addEventListener("onProjectInvite", (event) => {
        // Handle SSE message received
        console.log("Project Invite", "SSE message received:", event.data);
      });

      eventSource.addEventListener("onTaskCommentNew", (event) => {
        // Handle SSE message received
        console.log("Task Comment New", "SSE message received:", event.data);
      });

      eventSource.addEventListener("onTaskStatusUpdate", (event) => {
        // Handle SSE message received
        console.log("Task Status Update", "SSE message received:", event.data);
      });
    }
  }, [isLoggedIn]);

  return (
    <div className="App">
      <header className="App-header">
        <img src={logo} className="App-logo" alt="logo" />
        <p>
          Edit <code>src/App.js</code> and save to reload.
        </p>
        <a
          className="App-link"
          href="https://reactjs.org"
          target="_blank"
          rel="noopener noreferrer"
        >
          Learn React
        </a>
      </header>
    </div>
  );
}

export default App;
