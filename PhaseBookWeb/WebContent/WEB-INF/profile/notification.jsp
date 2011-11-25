<%@ page import="phasebook.controller.*"%>
<%@ page import="phasebook.user.*" %>
<%@ page import="phasebook.post.*" %>
<%@ page import="phasebook.lotterybet.*" %>
<%@ page import="phasebook.friendship.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.text.*" %>

<h1>Notifications</h1>

<%
	PhasebookUserRemote userBean = Utils.getUserBean();
	PhasebookUser me = userBean.getUserById(session.getAttribute("id"),
			session.getAttribute("id"), session.getAttribute("password"));
	if (Utils.getNumberNotifications(me,
			session.getAttribute("id"), session.getAttribute("password")) == 0) {
%>
		There are no new notifications.
<%
	}
	else {
	List<Post> posts = (List<Post>)Utils.getPostBean().getUnreadPosts(me,
			session.getAttribute("id"), session.getAttribute("password"));
	if (posts.size() > 0) {
%>
<h2>New posts</h2>
<%
	for (int i=posts.size()-1; i>=0; i--) {
		PhasebookUser user = posts.get(i).getFromUser();
%>
	<table width="100%">
		<tr>
			<td width="60" style="vertical-align: top">
				<% if (user.getPhoto()!=null){ 
					String photoURL = Utils.MAIN_PATH + user.getId() + "/"+user.getPhoto().getName();
				%>
					<%= Utils.a("user&id="+user.getId(), Utils.smallImg(photoURL)) %>
				<% } %>
			</td>
			<td>
				<b class="user"><%= Utils.a("user&id="+user.getId(), Utils.text(user.getName())) %></b>
				<% if (posts.get(i).isPrivate_()) { %><i>(private)</i><% } %><br />
				<% if (posts.get(i).getPhoto()!=null){ 
					String photoURL = Utils.MAIN_PATH+me.getId()+"/"+posts.get(i).getPhoto().getName();
				%>
					<br /> <%= Utils.aAbsolute(photoURL, Utils.img(photoURL)) %>
				<%} %>
				<br /><%= Utils.text(posts.get(i).getText()) %>
			</td>
		</tr>
	</table>
<% }} %>

<%
	List<LotteryBet> bets = (List<LotteryBet>)Utils.getLotteryBetBean().checkUnreadBetResults(me,
			session.getAttribute("id"), session.getAttribute("password"));
	if (bets.size() > 0) {
%>
<h2>New bet results</h2>
<%
	for (int i=bets.size()-1; i>=0; i--) {
		LotteryBet bet = bets.get(i);
%>
	<% if (bet.getBetNumber() == bet.getLottery().getLotteryNumber()) { %><b><% } %>
	Number <%= bet.getBetNumber() %> at
	<%
		DateFormat dateFormat = new SimpleDateFormat("HH:mm - dd/MM/yyyy");
		String date = dateFormat.format(bet.getLottery().getLotteryDate().getTime());
	%>
	<%= date %>
	(number <%= bet.getLottery().getLotteryNumber() %> won)
	<% if (bet.getBetNumber() == bet.getLottery().getLotteryNumber()) { %>
		 - You won <%= bet.getValueWon() %> L&euro;!</b>
	<% } %><br />
<% }} %>

<%
	List<Friendship> requests = (List<Friendship>)Utils.getFriendshipBean().getNewFriendshipInvites(me,
			session.getAttribute("id"), session.getAttribute("password"));
	if (requests.size() > 0) {
%>
<h2>New friendship requests</h2>
<%
	for (int i=requests.size()-1; i>=0; i--) {
		PhasebookUser user = requests.get(i).getHostUser();
%>
	<table width="100%">
		<tr>
			<td width="60">
				<% if (user.getPhoto()!=null){ 
					String photoURL = Utils.MAIN_PATH + user.getId() + "/"+user.getPhoto().getName();
				%>
					<%= Utils.a("user&id="+user.getId(), Utils.smallImg(photoURL)) %>
				<% } %>
			</td>
			<td>
				<b class="user"><%= Utils.a("user&id="+user.getId(), Utils.text(user.getName())) %></b><br />
				<i><%= Utils.text(user.getEmail()) %></i>
			</td>
		</tr>
	</table>
<% }} %>

<%
	List<Friendship> confirmations = (List<Friendship>)Utils.getFriendshipBean().getNewFriendshipAcceptances(me,
			session.getAttribute("id"), session.getAttribute("password"));
	if (confirmations.size() > 0) {
%>
<h2>New friendship confirmations</h2>
<%
	for (int i=confirmations.size()-1; i>=0; i--) {
		PhasebookUser user = confirmations.get(i).getInvitedUser();
%>
	<table width="100%">
		<tr>
			<td width="60">
				<% if (user.getPhoto()!=null){ 
					String photoURL = Utils.MAIN_PATH + user.getId() + "/"+user.getPhoto().getName();
				%>
					<%= Utils.a("user&id="+user.getId(), Utils.smallImg(photoURL)) %>
				<% } %>
			</td>
			<td>
				<b class="user"><%= Utils.a("user&id="+user.getId(), Utils.text(user.getName())) %></b><br />
				<i><%= Utils.text(user.getEmail()) %></i>
			</td>
		</tr>
	</table>
<% }} %>

<%
	Utils.getPostBean().readUnreadPosts(me,
			session.getAttribute("id"), session.getAttribute("password"));
	Utils.getLotteryBetBean().readUnreadBets(me,
			session.getAttribute("id"), session.getAttribute("password"));
	Utils.getFriendshipBean().readUnreadFriendshipAcceptances(me,
			session.getAttribute("id"), session.getAttribute("password"));
	}
%>