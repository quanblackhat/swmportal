var selected = [];

// save checked for
function saveChecked(id) {
	var checkbox = document.getElementById(id);
	var index = $.inArray($(checkbox).val(), selected);
	if (checkbox.checked && index < 0) {
		selected.push($(checkbox).val());
	} else if (!checkbox.checked && index >= 0) {
		// remove check all
		$('#inputSelectAll').prop('checked', false);
		selected.splice(index, 1);
	}

	// set check all
	if (isCheckAll()) {
		$('#inputSelectAll').prop('checked', true);
	}

	// load check
	loadChecked();
	
	// set hidden button
	hiddenButton();
}

// if all checkbox is checked set check all true
function isCheckAll() {
	var temp = true;
	$("input[name=chooses]").each(function() {
		if (!this.checked) {
			temp = false;
			return temp;
		}
	});
	return temp;
}

// save checked all
function saveCheckedAll() {
	$("input[name=chooses]").each(function() {
		var index = $.inArray($(this).val(), selected);
		if (this.checked && index < 0) {
			selected.push($(this).val());
		} else if (!this.checked && index >= 0) {
			selected.splice(index, 1);
		}
	});
	
	// set hidden button
	hiddenButton();
}

// set checked all
function selectAll() {
	if (document.getElementById('inputSelectAll').checked) {
		$('.fucker').prop('checked', true);
	} else {
		$('.fucker').prop('checked', false);
	}
	saveCheckedAll();
}

// set hidden button for pages
function hiddenButton() {
	// detail page
	if (selected.length < 1) {
		$('#deletes').attr("style",
				"pointer-events: none; cursor: default; opacity:0.5; margin-bottom: 10px; width: 100px");
	} else {
		$('#deletes').attr("style", "margin-bottom: 10px; width: 100px");
	}
}

// add checkbox another page for current page
function loadChecked() {
	var html = '';
	for (var i = 0; i < selected.length; i++) {
		var checkbox = document.getElementById(selected[i]);
			html += '<input type="checkbox" checked name="chooses" value="'
					+ selected[i] + '">';
		$('#' + selected[i]).prop('checked', true);
	}
	$('#addcheckbox').html(html);

	// set check all
	if (isCheckAll()) {
		$('#inputSelectAll').prop('checked', true);
	}
}

function getSelectedString() {
    var stringCodes = '';
    selected.forEach(function(element) {
        stringCodes += element;
        stringCodes += ',';
    });
    return stringCodes;

}