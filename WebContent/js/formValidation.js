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
