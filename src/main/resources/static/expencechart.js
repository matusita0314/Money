
var ctx = document.getElementById('expenceChart').getContext('2d');
var chart = new Chart(ctx, {
	type: 'bar',
	data: {
		labels: ['1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月', '9月', '10月', '11月', '12月'],
		datasets: [
			{
				label: '支出',
				backgroundColor: '#4CAF50',
				borderColor: '#4CAF50', 
				borderWidth: 1,
				data: monthlyExpences 
			}
		]
	},
	options: {
		responsive: true,
		scales: {
			y: {
				beginAtZero: true
			}
		}
	}
});
