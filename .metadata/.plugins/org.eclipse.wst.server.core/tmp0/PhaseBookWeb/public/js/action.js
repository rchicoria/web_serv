function showPopup()
{
	document.getElementById("menupopup").style.display = 'block';
}

function hidePopup()
{
	document.getElementById("menupopup").style.display = 'none';
}

function selectPosts()
{
	document.getElementById("tabposts").style.display = 'block';
	document.getElementById("tab1").className = 'active';
	document.getElementById("tabphotos").style.display = 'none';
	document.getElementById("tab2").className = 'inactive';
	document.getElementById("tabfriends").style.display = 'none';
	document.getElementById("tab3").className = 'inactive';
}

function selectPhotos()
{
	document.getElementById("tabposts").style.display = 'none';
	document.getElementById("tab1").className = 'inactive';
	document.getElementById("tabphotos").style.display = 'block';
	document.getElementById("tab2").className = 'active';
	document.getElementById("tabfriends").style.display = 'none';
	document.getElementById("tab3").className = 'inactive';
}

function selectFriends()
{
	document.getElementById("tabposts").style.display = 'none';
	document.getElementById("tab1").className = 'inactive';
	document.getElementById("tabphotos").style.display = 'none';
	document.getElementById("tab2").className = 'inactive';
	document.getElementById("tabfriends").style.display = 'block';
	document.getElementById("tab3").className = 'active';
}