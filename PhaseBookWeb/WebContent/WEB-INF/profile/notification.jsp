<%@ page import="phasebook.controller.*"%>
<%@ page import="phasebook.user.*" %>
<%@ page import="phasebook.post.*" %>
<%@ page import="phasebook.lotterybet.*" %>
<%@ page import="phasebook.friendship.*" %>
<%@ page import="phasebook.photo.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.text.*" %>

<h1>Notifications</h1>

<%
	PhasebookUserRemote userBean = Utils.getUserBean();
	PhasebookUser me = userBean.getUserById(session.getAttribute("id"),
			session.getAttribute("id"), session.getAttribute("password"));
	if (Utils.getNumberNotifications(me.getId(),
			session.getAttribute("id"), session.getAttribute("password")) == 0) {
%>
		There are no new notifications.
<%
	}
	else {
	List<?> posts = Utils.getPostBean().getUnreadPosts(me.getId(),
			session.getAttribute("id"), session.getAttribute("password"));
	if (posts.size() > 0) {
%>
<h2>New posts</h2>
<%
	for (int i=posts.size()-1; i>=0; i--) {
		Post post = (Post) posts.get(i);
		PhasebookUser user = userBean.getUserById(post.getFromUserId(),session.getAttribute("id"), session.getAttribute("password"));
%>
	<table style="width: 100%">
		<tr>
			<td width="60" style="vertical-align: top">
				<% if (user.getPhotoId()!=-1){
					PhotoRemote photoBean = Utils.getPhotoBean();
					String photoURL = Utils.MAIN_PATH + user.getId() + "/"+photoBean.getPhotoById(""+user.getPhotoId(), session.getAttribute("id"), session.getAttribute("password")).getName();
				%>
					<%= Utils.a("user&id="+user.getId(), Utils.smallImg(photoURL)) %>
				<% } %>
			</td>
			<td>
				<b class="user"><%= Utils.a("user&id="+user.getId(), Utils.text(user.getName())) %></b>
				<% if (post.isPrivate_()) { %><i>(private)</i><% } %><br />
				<% if (post.getPhotoId()!=-1){
					PhotoRemote photoBean = Utils.getPhotoBean();
					String photoURL = Utils.MAIN_PATH+me.getId()+"/"+photoBean.getPhotoById(""+post.getPhotoId(), session.getAttribute("id"), session.getAttribute("password")).getName();
				%>
					<br /> <%= Utils.aAbsolute(photoURL, Utils.img(photoURL)) %>
				<%} %>
				<br /><%= Utils.text(post.getText()) %>
			</td>
		</tr>
	</table>
<% }} %>

<%
	List<?> bets = Utils.getLotteryBetBean().checkUnreadBetResults(me.getId(),
			session.getAttribute("id"), session.getAttribute("password"));
	if (bets.size() > 0) {
%>
<h2>New bet results</h2>
<%
	for (int i=bets.size()-1; i>=0; i--) {
		LotteryBet bet = (LotteryBet) bets.get(i);
%>
	<% if (bet.getBetNumber() == bet.getLotteryNumber()) { %><b><% } %>
	Number <%= bet.getBetNumber() %> at
	<%
		DateFormat dateFormat = new SimpleDateFormat("HH:mm - dd/MM/yyyy");
		String date = dateFormat.format(bet.getLotteryDate().getTime());
	%>
	<%= date %>
	(number <%= bet.getLotteryNumber() %> won)
	<% if (bet.getBetNumber() == bet.getLotteryNumber()) { %>
		 - You won <%= bet.getValueWon() %> L&euro;!</b>
	<% } %><br />
<% }} %>

<%
	List<?> requests = Utils.getFriendshipBean().getNewFriendshipInvites(me.getId(),
			session.getAttribute("id"), session.getAttribute("password"));
	if (requests.size() > 0) {
%>
<h2>New friendship requests</h2>
<%
	for (int i=requests.size()-1; i>=0; i--) {
		Friendship friendship = (Friendship) requests.get(i);
		PhasebookUser user = userBean.getUserById(friendship.getHostUserId(), session.getAttribute("id"), session.getAttribute("password"));
%>
	<table style="width: 100%">
		<tr>
			<td width="60">
				<% if (user.getPhotoId()!=-1){
					PhotoRemote photoBean = Utils.getPhotoBean();
					String photoURL = Utils.MAIN_PATH + user.getId() + "/"+photoBean.getPhotoById(""+user.getPhotoId(), session.getAttribute("id"), session.getAttribute("password")).getName();
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
	List<?> confirmations = Utils.getFriendshipBean().getNewFriendshipAcceptances(me.getId(),
			session.getAttribute("id"), session.getAttribute("password"));
	if (confirmations.size() > 0) {
%>
<h2>New friendship confirmations</h2>
<%
	for (int i=confirmations.size()-1; i>=0; i--) {
		Friendship friendship = (Friendship) confirmations.get(i);
		PhasebookUser user = userBean.getUserById(friendship.getInvitedUserId(), session.getAttribute("id"), session.getAttribute("password"));
%>
	<table style="width: 100%">
		<tr>
			<td width="60">
				<% if (user.getPhotoId()!=-1){ 
					PhotoRemote photoBean = Utils.getPhotoBean();
					String photoURL = Utils.MAIN_PATH + user.getId() + "/"+photoBean.getPhotoById(""+user.getPhotoId(), session.getAttribute("id"), session.getAttribute("password")).getName();
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
	Utils.getPostBean().readUnreadPosts(me.getId(),
			session.getAttribute("id"), session.getAttribute("password"));
	Utils.getLotteryBetBean().readUnreadBets(me.getId(),
			session.getAttribute("id"), session.getAttribute("password"));
	Utils.getFriendshipBean().readUnreadFriendshipAcceptances(me.getId(),
			session.getAttribute("id"), session.getAttribute("password"));
	}
%>