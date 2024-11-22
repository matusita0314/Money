
var ctx = document.getElementById('incomeChart').getContext('2d');
var chart = new Chart(ctx, {
	type: 'bar',
	data: {
		labels: ['1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月', '9月', '10月', '11月', '12月'],
		datasets: [
			{
				label: '収入',
				backgroundColor: '#4CAF50',
				borderColor: '#4CAF50',
				borderWidth: 1,
				data: monthlyIncomes
			}
		]
	},
	options: {
		responsive: true,
		scales: {
			y: {
				suggestedMax: 100000,
				suggestedMin: 0,   
				ticks: {
					stepSize: 10000,  
					callback: function(value) {
						return value + '円'; 
					}
				}
			}
		}
	}
});
