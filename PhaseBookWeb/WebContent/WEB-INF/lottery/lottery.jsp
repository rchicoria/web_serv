<%@ page import="phasebook.controller.*" %>
<%@ page import="phasebook.lotterybet.*" %>
<%@ page import="java.util.List" %>
<%@ page import="java.text.*" %>

<img id="imgcharity" src="http://www.medicalaidfilms.org/images/10.jpg">

<h1>Charity Lottery</h1>

<p>
	By placing bets on our Lottery you are helping many charity associations raise funds for social projects.
	The more you bet, the more you are helping!
</p>
<p>
	To bet, simply guess a number between 1 and 100. You pay 1 L&euro; for every bet you do.
	If you guessed the right number you win 50 L&euro;.
</p>

<h2>
	Next Draw:
	<%
		if (Utils.getLotteryBean().nextDrawDate(session.getAttribute("id"), session.getAttribute("password"))==null) {
	%>
			No draws at the moment, come back later
	<% } else { %>
		<%= Utils.getLotteryBean().nextDrawDate(session.getAttribute("id"), session.getAttribute("password")) %>
	<% } %>
</h2>

<% if (session.getAttribute("errorBet") != null) { %>
	<p style="color:red"><%= session.getAttribute("errorBet") %></p>
<% session.removeAttribute("errorBet");} %>

<% if (Utils.getLotteryBean().nextDrawDate(session.getAttribute("id"), session.getAttribute("password"))!=null) { %>
<form method="POST" action="BetForm">

	<select name="number">
		<% for (int i=1; i<=100; i++) { %>
			<option value="<%= i %>"><%= i %></option>
		<% } %>
	</select>
	<input type="submit" value="Place bet" <% if(Utils.getUserBean().getUserById(session.getAttribute("id"),
			session.getAttribute("id"), session.getAttribute("password")).getMoney() < 1){ %>disabled="disabled"<% } %>>
	<p class="tip">
		(costs 1 L&euro;, you have <%= Utils.getUserBean().getUserById(session.getAttribute("id"),
				session.getAttribute("id"), session.getAttribute("password")).getMoney() %> L&euro;)
	</p>
	
</form>
<% } %>

<h2>Get more money</h2>

<% if (session.getAttribute("error") != null) { %>
	<p style="color:red"><%= session.getAttribute("error") %></p>
<% session.removeAttribute("error");} %>

<form method="POST" action="GetMoneyForm">

	<input type="text" name="money">
	<input type="submit" value="Deposit L&euro;">

</form>

<h2>Current bets</h2>
<p>
	<%
		LotteryBetRemote lotteryBet = Utils.getLotteryBetBean();
		List<LotteryBet> bets = lotteryBet.userCurrentBets(session.getAttribute("id"),
				session.getAttribute("id"), session.getAttribute("password"));
		if (bets == null || bets.size() == 0) {
	%>
			You have no bets for the next draw.
	<%
		}
		else {
			for (int i=0; i<bets.size(); i++) {
				LotteryBet bet = bets.get(i);
	%>
				Number <%= bet.getBetNumber() %><br />
	<%
			}
		}
	%>
</p>

<h2>Old bets</h2>

<p>
	<%
		bets = lotteryBet.userOldBets(session.getAttribute("id"),
				session.getAttribute("id"), session.getAttribute("password"));
		if (bets == null || bets.size() == 0) {
	%>
			You have never placed any bet.
	<%
		}
		else {
			for (int i=0; i<bets.size(); i++) {
				LotteryBet bet = bets.get(i);
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
	<%
			}
		}
	%>
</p>