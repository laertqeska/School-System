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

    // Setup character counter
    const reasonTextarea = document.getElementById('reason');
    const charCount = document.getElementById('char-count');

    reasonTextarea.addEventListener('input', () => {
        const length = reasonTextarea.value.length;
        charCount.textContent = length;

        // Update color based on length
        if (length < 10) {
            charCount.style.color = '#e74c3c';
        } else if (length > 480) {
            charCount.style.color = '#f39c12';
        } else {
            charCount.style.color = '#27ae60';
        }
    });

    // Setup form submission
    document.getElementById('rejection-form').addEventListener('submit', handleReject);

    // Setup cancel button
    document.getElementById('cancel-btn').addEventListener('click', () => {
        window.location.href = '/rector/dashboard.html';
    });
}

async function handleReject(event) {
    event.preventDefault();

    const reasonTextarea = document.getElementById('reason');
    const reason = reasonTextarea.value.trim();
    const reasonError = document.getElementById('reason-error');

    // Validate reason
    if (reason.length < 10) {
        reasonError.textContent = 'Reason must be at least 10 characters long';
        reasonError.style.display = 'block';
        reasonTextarea.focus();
        return;
    }

    if (reason.length > 500) {
        reasonError.textContent = 'Reason must not exceed 500 characters';
        reasonError.style.display = 'block';
        reasonTextarea.focus();
        return;
    }

    reasonError.style.display = 'none';

    const confirmBtn = document.getElementById('confirm-btn');
    const cancelBtn = document.getElementById('cancel-btn');

    // Disable buttons
    confirmBtn.disabled = true;
    cancelBtn.disabled = true;
    reasonTextarea.disabled = true;
    confirmBtn.textContent = 'Rejecting...';

    try {
        const response = await fetch(`/api/rector/faculties/reject-by-token?token=${token}`, {
            method: 'POST',
            headers: {
                'Authorization': `Bearer ${jwt}`,
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ reason: reason })
        });

        if (!response.ok) {
            const errorData = await response.json();
            throw new Error(errorData.message || 'Failed to reject faculty');
        }

        // Clear pending approval from storage
        sessionStorage.removeItem('pending_approval_token');
        sessionStorage.removeItem('pending_approval_action');

        // Show success message
        showSuccess('Faculty rejected successfully!');

        // Redirect after 2 seconds
        setTimeout(() => {
            window.location.href = '/rector/dashboard.html';
        }, 2000);

    } catch (error) {
        // Re-enable form
        confirmBtn.disabled = false;
        cancelBtn.disabled = false;
        reasonTextarea.disabled = false;
        confirmBtn.textContent = 'Confirm Rejection';

        reasonError.textContent = error.message;
        reasonError.style.display = 'block';
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