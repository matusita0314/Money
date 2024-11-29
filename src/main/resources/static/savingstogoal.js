var ctx = document.getElementById('savingsGoalChart');
var myDoughnutChart = new Chart(ctx, {
    type: 'doughnut',
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
    },
    options: {
        responsive: true,
        plugins: {
            title: {
                display: true, 
                text: '目標の達成状況', 
                font: {
                    size: 18
                }
            },
            
        },
    }
});
