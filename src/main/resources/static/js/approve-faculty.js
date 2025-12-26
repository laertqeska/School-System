// Get token from URL
const urlParams = new URLSearchParams(window.location.search);
const token = urlParams.get('token');

// Check authentication
const jwt = localStorage.getItem('jwt_token');
if (!jwt) {
    // Not logged in, redirect to landing page
    window.location.href = `/approval-landing.html?token=${token}`;
} else {
    loadApprovalDetails();
}

async function loadApprovalDetails() {
    try {
        const response = await fetch(`/api/approvals/faculty/${token}`, {
            headers: {
                'Authorization': `Bearer ${jwt}`
            }
        });

        if (!response.ok) {
            throw new Error('Failed to load faculty details');
        }

        const data = await response.json();

        // Check if already used or expired
        if (data.isUsed) {
            showError('This approval request has already been processed.');
            return;
        }

        if (data.isExpired) {
            showError('This approval link has expired.');
            return;
        }

        // Display faculty details
        displayFacultyDetails(data);

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

    // Setup buttons
    document.getElementById('confirm-btn').addEventListener('click', handleApprove);
    document.getElementById('cancel-btn').addEventListener('click', () => {
        window.location.href = '/rector/dashboard.html';
    });
}

async function handleApprove() {
    const confirmBtn = document.getElementById('confirm-btn');
    const cancelBtn = document.getElementById('cancel-btn');

    // Disable buttons
    confirmBtn.disabled = true;
    cancelBtn.disabled = true;
    confirmBtn.textContent = 'Approving...';

    try {
        const response = await fetch(`/api/rector/faculties/approve-by-token?token=${token}`, {
            method: 'POST',
            headers: {
                'Authorization': `Bearer ${jwt}`,
                'Content-Type': 'application/json'
            }
        });

        if (!response.ok) {
            const errorData = await response.json();
            throw new Error(errorData.message || 'Failed to approve faculty');
        }

        // Clear pending approval from storage
        sessionStorage.removeItem('pending_approval_token');
        sessionStorage.removeItem('pending_approval_action');

        // Show success message
        showSuccess('Faculty approved successfully!');

        // Redirect after 2 seconds
        setTimeout(() => {
            window.location.href = '/rector/dashboard.html';
        }, 2000);

    } catch (error) {
        // Re-enable buttons
        confirmBtn.disabled = false;
        cancelBtn.disabled = false;
        confirmBtn.textContent = 'Confirm Approval';

        showError(error.message);
    }
}

function showSuccess(message) {
    const content = document.getElementById('content');
    content.innerHTML = `
        <div class="success-box">
            <div class="success-icon">✓</div>
            <h2>Success!</h2>
            <p>${message}</p>
            <p class="redirect-message">Redirecting to dashboard...</p>
        </div>
    `;
}

function showError(message) {
    document.getElementById('loading').style.display = 'none';
    document.getElementById('content').style.display = 'none';
    document.getElementById('error-message').style.display = 'block';
    document.getElementById('error-text').textContent = message;
}