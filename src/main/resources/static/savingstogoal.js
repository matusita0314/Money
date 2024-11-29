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
<<<<<<< HEAD
                text: '目標の達成状況', 
=======
                text: '貯金目標の達成状況', 
>>>>>>> fd71f6396410125131557066113d3c4e43805c16
                font: {
                    size: 18
                }
            },
<<<<<<< HEAD
            
=======
            tooltip: {
                callbacks: {
                    label: function(tooltipItem) {
                        const total = totalMoney + (goal - totalMoney);
                        const value = tooltipItem.raw;
                        const percentage = ((value / total) * 100).toFixed(2);
                        return `${tooltipItem.label}: ${value}円 (${percentage}%)`;
                    }
                }
            }
>>>>>>> fd71f6396410125131557066113d3c4e43805c16
        },
    }
});
