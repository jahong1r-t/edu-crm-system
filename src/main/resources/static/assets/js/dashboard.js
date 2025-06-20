// Global state
let uploadedFiles = [];

// Initialize application
document.addEventListener('DOMContentLoaded', function() {
    initializeDropdowns();
    initializeFileUpload();
    initializeChat();
    initializeProfileForm();
});

// Dropdown Functions
function initializeDropdowns() {
    // Notification dropdown
    const notificationBtn = document.getElementById('notificationBtn');
    const notificationDropdown = document.getElementById('notificationDropdown');

    if (notificationBtn && notificationDropdown) {
        notificationBtn.addEventListener('click', (e) => {
            e.stopPropagation();
            notificationDropdown.classList.toggle('show');
            // Close profile dropdown if open
            const profileDropdown = document.getElementById('profileDropdown');
            if (profileDropdown) {
                profileDropdown.classList.remove('show');
            }
        });
    }

    // Profile dropdown
    const profileBtn = document.getElementById('profileBtn');
    const profileDropdown = document.getElementById('profileDropdown');

    if (profileBtn && profileDropdown) {
        profileBtn.addEventListener('click', (e) => {
            e.stopPropagation();
            profileDropdown.classList.toggle('show');
            // Close notification dropdown if open
            if (notificationDropdown) {
                notificationDropdown.classList.remove('show');
            }
        });
    }

    // Close dropdowns when clicking outside
    document.addEventListener('click', () => {
        if (notificationDropdown) notificationDropdown.classList.remove('show');
        if (profileDropdown) profileDropdown.classList.remove('show');
    });

    // Mark all notifications as read
    const markAllRead = document.querySelector('.mark-all-read');
    if (markAllRead) {
        markAllRead.addEventListener('click', () => {
            const unreadItems = document.querySelectorAll('.notification-item.unread');
            unreadItems.forEach(item => item.classList.remove('unread'));

            // Update notification badge
            const badge = document.querySelector('.notification-badge');
            if (badge) {
                badge.textContent = '0';
                badge.style.display = 'none';
            }
        });
    }
}

// File Upload Functions
function initializeFileUpload() {
    const uploadArea = document.getElementById('uploadArea');
    const fileInput = document.getElementById('fileInput');
    const uploadButton = document.getElementById('uploadButton');
    const uploadedFilesContainer = document.getElementById('uploadedFiles');

    if (!uploadArea || !fileInput || !uploadButton) return;

    // Click to browse files
    uploadButton.addEventListener('click', (e) => {
        e.preventDefault();
        fileInput.click();
    });

    uploadArea.addEventListener('click', () => {
        fileInput.click();
    });

    // File input change
    fileInput.addEventListener('change', (e) => {
        handleFiles(e.target.files);
    });

    // Drag and drop
    uploadArea.addEventListener('dragover', (e) => {
        e.preventDefault();
        uploadArea.classList.add('dragover');
    });

    uploadArea.addEventListener('dragleave', () => {
        uploadArea.classList.remove('dragover');
    });

    uploadArea.addEventListener('drop', (e) => {
        e.preventDefault();
        uploadArea.classList.remove('dragover');
        handleFiles(e.dataTransfer.files);
    });
}

function handleFiles(files) {
    const allowedTypes = ['image/jpeg', 'image/png', 'image/gif', 'application/zip'];
    const maxSize = 10 * 1024 * 1024; // 10MB

    Array.from(files).forEach(file => {
        if (!allowedTypes.includes(file.type)) {
            alert(`File type not supported: ${file.name}`);
            return;
        }

        if (file.size > maxSize) {
            alert(`File too large: ${file.name}. Maximum size is 10MB.`);
            return;
        }

        const fileData = {
            id: Date.now() + Math.random(),
            name: file.name,
            size: formatFileSize(file.size),
            type: file.type,
            file: file
        };

        uploadedFiles.push(fileData);
        displayUploadedFile(fileData);
    });
}

function displayUploadedFile(fileData) {
    const uploadedFilesContainer = document.getElementById('uploadedFiles');
    if (!uploadedFilesContainer) return;

    const fileElement = document.createElement('div');
    fileElement.className = 'uploaded-file';
    fileElement.innerHTML = `
    <div class="file-info">
      <div class="file-name">${fileData.name}</div>
      <div class="file-size">${fileData.size}</div>
    </div>
    <button class="file-remove" onclick="removeFile(${fileData.id})">
      <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
        <polyline points="3,6 5,6 21,6"></polyline>
        <path d="M19,6v14a2,2 0 0,1 -2,2H7a2,2 0 0,1 -2,-2V6m3,0V4a2,2 0 0,1 2,-2h4a2,2 0 0,1 2,2v2"></path>
      </svg>
    </button>
  `;

    uploadedFilesContainer.appendChild(fileElement);
}

function removeFile(fileId) {
    uploadedFiles = uploadedFiles.filter(file => file.id !== fileId);

    const fileElements = document.querySelectorAll('.uploaded-file');
    fileElements.forEach(element => {
        const removeButton = element.querySelector('.file-remove');
        if (removeButton && removeButton.getAttribute('onclick').includes(fileId)) {
            element.remove();
        }
    });
}

function formatFileSize(bytes) {
    if (bytes === 0) return '0 Bytes';
    const k = 1024;
    const sizes = ['Bytes', 'KB', 'MB', 'GB'];
    const i = Math.floor(Math.log(bytes) / Math.log(k));
    return parseFloat((bytes / Math.pow(k, i)).toFixed(2)) + ' ' + sizes[i];
}

// Chat Functions
function initializeChat() {
    const chatInput = document.getElementById('chatInput');
    const chatSendBtn = document.getElementById('chatSendBtn');
    const chatMessages = document.getElementById('chatMessages');

    if (!chatInput || !chatSendBtn || !chatMessages) return;

    // Send message on button click
    chatSendBtn.addEventListener('click', sendMessage);

    // Send message on Enter key
    chatInput.addEventListener('keypress', (e) => {
        if (e.key === 'Enter' && !e.shiftKey) {
            e.preventDefault();
            sendMessage();
        }
    });

    // Auto-scroll to bottom of messages
    scrollToBottom();
}

function sendMessage() {
    const chatInput = document.getElementById('chatInput');
    const chatMessages = document.getElementById('chatMessages');

    if (!chatInput || !chatMessages) return;

    const messageText = chatInput.value.trim();
    if (!messageText) return;

    // Create message element
    const messageElement = document.createElement('div');
    messageElement.className = 'message sent';
    messageElement.innerHTML = `
    <div class="message-content">
      <p>${messageText}</p>
      <span class="message-time">${getCurrentTime()}</span>
    </div>
  `;

    // Add message to chat
    chatMessages.appendChild(messageElement);

    // Clear input
    chatInput.value = '';

    // Scroll to bottom
    scrollToBottom();

    // Simulate teacher response after a delay
    setTimeout(() => {
        simulateTeacherResponse();
    }, 1000 + Math.random() * 2000);
}

function simulateTeacherResponse() {
    const chatMessages = document.getElementById('chatMessages');
    if (!chatMessages) return;

    const responses = [
        "Thank you for your message! I'll review this and get back to you shortly.",
        "Great question! Let me think about this and provide you with a detailed answer.",
        "I appreciate your engagement in class. This shows you're really thinking about the material.",
        "That's an interesting point. Have you considered approaching it from a different angle?",
        "I'm glad you're asking questions - it helps me understand what concepts need more clarification."
    ];

    const randomResponse = responses[Math.floor(Math.random() * responses.length)];

    const messageElement = document.createElement('div');
    messageElement.className = 'message received';
    messageElement.innerHTML = `
    <img src="https://images.pexels.com/photos/415829/pexels-photo-415829.jpeg?auto=compress&cs=tinysrgb&w=100&h=100&dpr=2" alt="Teacher" class="message-avatar">
    <div class="message-content">
      <p>${randomResponse}</p>
      <span class="message-time">${getCurrentTime()}</span>
    </div>
  `;

    chatMessages.appendChild(messageElement);
    scrollToBottom();
}

function scrollToBottom() {
    const chatMessages = document.getElementById('chatMessages');
    if (chatMessages) {
        chatMessages.scrollTop = chatMessages.scrollHeight;
    }
}

function getCurrentTime() {
    const now = new Date();
    return now.toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' });
}

// Profile Form Functions
function initializeProfileForm() {
    const saveButton = document.querySelector('.btn-primary');
    if (saveButton && saveButton.textContent.trim() === 'Save Changes') {
        saveButton.addEventListener('click', (e) => {
            e.preventDefault();

            // Simulate saving
            saveButton.textContent = 'Saving...';
            saveButton.disabled = true;

            setTimeout(() => {
                saveButton.textContent = 'Save Changes';
                saveButton.disabled = false;

                // Show success message
                showSuccessMessage('Your profile has been updated successfully.');
            }, 1500);
        });
    }
}

function showSuccessMessage(message) {
    const formActions = document.querySelector('.form-actions');
    if (!formActions) return;

    // Remove existing success message
    const existingMessage = document.querySelector('.success-message');
    if (existingMessage) {
        existingMessage.remove();
    }

    const successMessage = document.createElement('div');
    successMessage.className = 'success-message';
    successMessage.innerHTML = `
    <div style="background: #dcfce7; color: #166534; padding: 12px 16px; border-radius: 8px; margin-top: 16px; border: 1px solid #bbf7d0;">
      <strong>Success!</strong> ${message}
    </div>
  `;

    formActions.parentNode.insertBefore(successMessage, formActions);

    // Remove success message after 3 seconds
    setTimeout(() => {
        successMessage.remove();
    }, 3000);
}

// Utility Functions
function updateNotificationBadge(count) {
    const badge = document.querySelector('.notification-badge');
    if (badge) {
        badge.textContent = count;
        badge.style.display = count > 0 ? 'block' : 'none';
    }
}

// Real-time updates simulation
setInterval(() => {
    // Simulate real-time notifications
    if (Math.random() > 0.98) { // 2% chance every second
        updateNotificationBadge(3 + Math.floor(Math.random() * 3));
    }
}, 1000);

// Export functions for global access
window.removeFile = removeFile;