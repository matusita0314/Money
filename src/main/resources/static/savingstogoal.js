
var ctx = document.getElementById('savingsGoalChart');
var myDoughnutChart = new Chart(ctx, {
	type: 'doughnut', // 円グラフに変更
	data: {
		labels: ['現在の貯金額', '残りの目標額'],
		datasets: [
			{
				data: [totalMoney, goal - totalMoney],
				backgroundColor: ['#4CAF50', 'rgba(200, 200, 200, 0.5)'], 
				borderColor: ['#4CAF50', 'rgba(200, 200, 200, 1)'],
				borderWidth: 1
			}
		]
	}
});