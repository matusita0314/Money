var ctx = document.getElementById('savingsGoalChart').getContext('2d');
var chart = new Chart(ctx, {
	type: 'bar', // 棒グラフ
	data: {
		labels: ['目標に対する現在の貯金'],
		datasets: [
			{
				label: '目標額',
				data: [goal], // 目標額
				backgroundColor: 'rgba(200, 200, 200, 0.5)', // 灰色で目標を表示
				borderColor: 'rgba(200, 200, 200, 1)',
				borderWidth: 1
			},
			{
				label: '現在の貯金額',
				data: [totalMoney], // 現在の貯金額
				backgroundColor: '#4CAF50', // 緑色で現在の貯金額を表示
				borderColor: '#4CAF50',
				borderWidth: 1
			}
		]
	},
	options: {
		indexAxis: 'z', // 水平グラフ
		responsive: true,
		scales: {
			x: {
				beginAtZero: true,
				max: goal // x軸の最大値を目標額に設定
			}
		},
		plugins: {
			tooltip: {
				callbacks: {
					label: function(context) {
						// 現在の貯金額が目標額の何％かを表示
						var percentage = (context.raw / goal * 100).toFixed(2);
						return context.dataset.label + ': ' + context.raw + '円 (' + percentage + '%)';
					}
				}
			}
		}
	}
});