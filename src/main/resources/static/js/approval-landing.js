// Get token from URL
const urlParams = new URLSearchParams(window.location.search);
const token = urlParams.get('token');

if (!token) {
    showError('No approval token provided');
} else {
    loadApprovalDetails();
}

async function loadApprovalDetails() {
    try {
        const response = await fetch(`/api/approvals/faculty/${token}`);

        if (!response.ok) {
            throw new Error('Invalid or expired approval link');
        }

        const data = await response.json();

        // Check if already used or expired
        if (data.isUsed) {
            showError('This approval request has already been processed.');
            return;
        }

        if (data.isExpired) {
            showError('This approval link has expired. Please contact the school admin.');
            return;
        }

        // Display faculty details
        displayFacultyDetails(data);

        // Check if user is already logged in
        checkAuthAndRedirect(data);

    } catch (error) {
        showError(error.message || 'Failed to load approval details');
    }
}

function displayFacultyDetails(data) {
    document.getElementById('loading').style.display = 'none';
    document.getElementById('content').style.display = 'block';

    document.getElementById('faculty-name').textContent = data.facultyName;
    document.getElementById('faculty-code').textContent = data.facultyCode;
    document.getElementById('school-name').textContent = data.schoolName;
    document.getElementById('created-by').textContent = data.createdByName;
    document.getElementById('created-at').textContent = data.createdAt;

    // Show appropriate action message
    const actionPrompt = document.getElementById('action-prompt');
    if (data.action === 'APPROVE') {
        actionPrompt.innerHTML = `
            <div class="approve-message">
                <span class="icon">✅</span>
                <p>You are about to <strong>APPROVE</strong> this faculty.</p>
            </div>
        `;
        actionPrompt.className = 'action-prompt approve';
    } else {
        actionPrompt.innerHTML = `
            <div class="reject-message">
                <span class="icon">❌</span>
                <p>You are about to <strong>REJECT</strong> this faculty.</p>
            </div>
        `;
        actionPrompt.className = 'action-prompt reject';
    }

    // Store action in sessionStorage for after login
    sessionStorage.setItem('pending_approval_token', token);
    sessionStorage.setItem('pending_approval_action', data.action);

    // Setup login button
    document.getElementById('login-btn').addEventListener('click', () => {
        // Redirect to login with return URL
        const returnUrl = data.action === 'APPROVE'
            ? `/approve-faculty.html?token=${token}`
            : `/reject-faculty.html?token=${token}`;

        window.location.href = `/login.html?returnUrl=${encodeURIComponent(returnUrl)}`;
    });
}

function checkAuthAndRedirect(data) {
    // Check if JWT token exists in localStorage
    const jwt = localStorage.getItem('jwt_token');

    if (jwt) {
        // User is already logged in, redirect to confirmation page
        if (data.action === 'APPROVE') {
            window.location.href = `/approve-faculty.html?token=${token}`;
        } else {
            window.location.href = `/reject-faculty.html?token=${token}`;
        }
    }
}

function showError(message) {
    document.getElementById('loading').style.display = 'none';
    document.getElementById('error-message').style.display = 'block';
    document.getElementById('error-text').textContent = message;
}