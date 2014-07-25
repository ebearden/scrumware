/**
 * Validation for forms.
 */
function taskFormValidation() {
	$('#taskForm').bootstrapValidator({
        message: 'This value is not valid',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            task_name: {
                message: 'Please enter a name',
                validators: {
                    notEmpty: {
                        message: 'The task name cannot be empty'
                    },
                    stringLength: {
                        min: 6,
                        max: 30,
                        message: 'The task name must be more than 6 and less than 30 characters long'
                    }
                }
            },
            description: {
                validators: {
                    notEmpty: {
                        message: 'Please enter a description for the task.'
                    },
                    stringLength: {
                        min: 10,
                        max: 1000,
                        message: 'The task description should be descriptive.'
                    }
                }
            }
        }
    });
}


function userFormValidation() {
	$('#userForm').bootstrapValidator({
        message: 'This value is not valid',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            user_name: {
                message: 'Please enter a user name',
                validators: {
                    notEmpty: {
                        message: 'The user name cannot be empty'
                    },
                    stringLength: {
                        min: 6,
                        max: 30,
                        message: 'The user name must be more than 6 and less than 30 characters long'
                    }
                }
            },
            first_name: {
                message: 'Please enter a first name',
                validators: {
                    notEmpty: {
                        message: 'The first name cannot be empty'
                    },
                    stringLength: {
                        min: 1,
                        max: 30,
                        message: 'The first name must be more than 6 and less than 30 characters long'
                    }
                }
            },
            last_name: {
                message: 'Please enter a last name',
                validators: {
                    notEmpty: {
                        message: 'The last name cannot be empty'
                    },
                    stringLength: {
                        min: 1,
                        max: 30,
                        message: 'The last name must be more than 6 and less than 30 characters long'
                    }
                }
            },
            email: {
                validators: {
                    notEmpty: {
                        message: 'Please enter an email address for the user.'
                    },
                    emailAddress: {
                        message: 'That is not a valid email address.'
                    }
                }
            }
        }
    });
}
