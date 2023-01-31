<template>
    <Category :categories="categories" :categoryStatus="categoryStatus"/>
    <div class="admin-home">
        <div class="overall-count-container">
            <div class="overall-count">
                <label for="" class="add-deco-img-left deco-img-user">전체 회원수</label><span>{{ overallCount.totalMember }} 명</span>
            </div>
            <div class="overall-count">
                <label for="" class="add-deco-img-left deco-img-meeting">전체 모임수</label><span>{{ overallCount.totalMeeting }} 개</span>
            </div>
            <div class="overall-count">
                <label for="" class="add-deco-img-left deco-img-plus">어제 가입한 회원수</label><span>{{ overallCount.newMemberYesterday }} 명</span>
            </div>
            <div class="overall-count">
                <label for="" class="add-deco-img-left deco-img-plus">어제 개설된 모임수</label><span>{{ overallCount.newMeetingYesterday }} 개</span>
            </div>
        </div>
        <div class="doughnut-chart-container">
            <div class="doughnut-chart">
                <span>카테고리 별 모임 수</span>
                <Doughnut class="doughnut-chart__chart" id="my-chart-id" :options="chartDoughnutOptions" :data="chartCategoryData"
                    v-if="category.isLoaded"></Doughnut>
            </div>
            <div class="doughnut-chart">
                <span>지역 별 모임 수</span>
                <Doughnut  class="doughnut-chart__chart"  id="my-chart-id2" :options="chartDoughnutOptions" :data="chartRegionData"
                    v-if="region.isLoaded">
                </Doughnut>
            </div>
            <div class="doughnut-chart">
                <span>연령대 별 모임 수</span>
                <Doughnut  class="doughnut-chart__chart"  id="my-chart-id3" :options="chartDoughnutOptions" :data="chartAgeRangeData"
                    v-if="ageRange.isLoaded"></Doughnut>
            </div>
            <div class="doughnut-chart">
                <span>연락방법 별 모임 수</span>
                <Doughnut  class="doughnut-chart__chart"  id="my-chart-id4" :options="chartDoughnutOptions" :data="chartContactTypeData"
                    v-if="contactType.isLoaded"></Doughnut>
            </div>
            <div class="doughnut-chart">
                <span>모집 중 / 마감된 모임 수</span>
                <Doughnut  class="doughnut-chart__chart"  id="my-chart-id5" :options="chartDoughnutOptions" :data="chartClosedNotClosedData"
                    v-if="closedNotClosed.isLoaded"></Doughnut>
            </div>
            <div class="doughnut-chart">
                <span>성별 모임 수</span>
                <Doughnut  class="doughnut-chart__chart"  id="my-chart-id6" :options="chartDoughnutOptions" :data="chartGenderCategoryData"
                    v-if="genderCategory.isLoaded"></Doughnut>
            </div>
            <div class="line-chart-container">
                <div class="line-chart">
                    <span>모임 개설 수</span>
                    <Line id="my-chart-id7" :options="chartLineOptions" :data="chartMeetingPerDayData"
                        v-if="meetingPerDay.isLoaded"></Line>
                </div>
                <div class="line-chart">
                    <span>회원 가입 수</span>
                    <Line id="my-chart-id8" :options="chartLineOptions" :data="chartNewMemberPerDayData"
                        v-if="newMemberPerDay.isLoaded"></Line>
                </div>
            </div>
        </div>
    </div>
</template>

<script setup>
import { Doughnut, Line } from 'vue-chartjs';
import { reactive,ref } from 'vue';
import { Chart as ChartJS, ArcElement, Tooltip, Legend, PointElement, LineElement, CategoryScale, LinearScale, Title } from 'chart.js'
import ChartDataLabels from 'chartjs-plugin-datalabels';
import Category from '../../../components/admin/Category.vue';
import { useRoute } from 'vue-router';

ChartJS.register(ArcElement, Tooltip, Legend, ChartDataLabels, PointElement, LineElement, CategoryScale, LinearScale, Title);

const emit = defineEmits(["menuChanged"]);
emit("menuChanged", "home");
const route = useRoute();

const categoryStatus = ref(0);

const categories = [
    {
        name: '대시보드',
        link: 'dashboard'
    }, 
];

const changeCategory = (category) => {
    let value;
    switch(category){
        case "dashboard":
        value = 0;
        break;
    }
    categoryStatus.value = value;
}

changeCategory(route.path.split('/')[3]);


const overallCount = reactive({
    totalMeeting: 0,
    totalMember: 0,
    newMeetingYesterday: 0,
    newMemberYesterday: 0,
})

const category = reactive({
    labels: [],
    nums: [],
    isLoaded: false,
    total: 0,
});

const region = reactive({
    labels: [],
    nums: [],
    isLoaded: false,
    total: 0,
});

const ageRange = reactive({
    labels: [],
    nums: [],
    isLoaded: false,
    total: 0,
});

const genderCategory = reactive({
    labels: [],
    nums: [],
    isLoaded: false,
    total: 0,
});

const contactType = reactive({
    labels: [],
    nums: [],
    isLoaded: false,
    total: 0,
});

const closedNotClosed = reactive({
    labels: [],
    nums: [],
    isLoaded: false,
    total: 0,
});

const meetingCount = reactive({
    category,
    region,
    ageRange,
    genderCategory,
    contactType,
    closedNotClosed,
})

const meetingPerDay = reactive({
    labels: [],
    nums: [],
    isLoaded: false,
    total: 0,
});

const newMemberPerDay = reactive({
    labels: [],
    nums: [],
    isLoaded: false,
    total: 0,
});

const chartCategoryData = {
    labels: category.labels,
    datasets: [
        {
            data: category.nums,
            label: '모임 수',
            backgroundColor: ['#004c6d','#035a7f','#056990','#0779a2','#0888b5','#0899c7','#07a9da','#04baec','#00cbff'],
            borderColor: "#eee",
            hoverBorderColor: "#eee",
        }
    ]
}
//https://www.heavy.ai/blog/12-color-palettes-for-telling-better-stories-with-your-data
//["#115f9a", "#1984c5", "#22a7f0", "#48b5c4", "#76c68f", "#a6d75b", "#c9e52f", "#d0ee11", "#d0f400"],
const chartRegionData = {
    labels: region.labels,
    datasets: [
        {
            data: region.nums,
            label: '모임 수',
            backgroundColor: ['#004c6d','#035a7f','#056990','#0779a2','#0888b5','#0899c7','#07a9da','#04baec','#00cbff'],
            borderColor: "#eee",
            hoverBorderColor: "#eee",
        }
    ]
}

const chartMeetingPerDayData = {
    labels: meetingPerDay.labels,
    datasets: [
        {
            borderWidth: 2, // 라인 넓이
            label: '모임 수', // 데이터 라벨
            backgroundColor: 'rgb(255,255,255, 0)',
            pointBackgroundColor: '#004c6d',
            fill: true, // 채우기
            tension: 0.3,
            borderColor: '#004c6d',
            pointBorderColor: '#004c6d',
            pointBorderWidth: 3,
            data: meetingPerDay.nums,
        }
    ]
}

const chartNewMemberPerDayData = {
    labels: newMemberPerDay.labels,
    datasets: [
        {
            borderWidth: 2, // 라인 넓이
            label: '회원 수', // 데이터 라벨
            backgroundColor: 'rgb(255,255,255, 0)',
            pointBackgroundColor: '#00cbff',
            fill: true, // 채우기
            tension: 0.3,
            borderColor: '#00cbff',
            pointBorderColor: '#00cbff',
            pointBorderWidth: 3,
            data: newMemberPerDay.nums,
        }
    ]
}

const chartAgeRangeData = {
    labels: ageRange.labels,
    datasets: [
        {
            data: ageRange.nums,
            label: '모임 수',
            backgroundColor: ['#004c6d','#035a7f','#056990','#0779a2','#0888b5','#0899c7','#07a9da','#04baec','#00cbff'],
            borderColor: "#eee",
            hoverBorderColor: "#eee",
        }
    ]
}

const chartContactTypeData = {
    labels: contactType.labels,
    datasets: [
        {
            data: contactType.nums,
            label: '모임 수',
            backgroundColor: ['#004c6d','#035a7f','#056990','#0779a2','#0888b5','#0899c7','#07a9da','#04baec','#00cbff'],
            borderColor: "#eee",
            hoverBorderColor: "#eee",
        }
    ]
}

const chartClosedNotClosedData = {
    labels: closedNotClosed.labels,
    datasets: [
        {
            data: closedNotClosed.nums,
            label: '모임 수',
            backgroundColor: ['#004c6d','#0888b5','#00cbff'],
            borderColor: "#eee",
            hoverBorderColor: "#eee",
        }
    ]
}

const chartGenderCategoryData = {
    labels: genderCategory.labels,
    datasets: [
        {
            data: genderCategory.nums,
            label: '모임 수',
            backgroundColor: ['#004c6d','#0888b5','#00cbff'],
            borderColor: "#eee",
            hoverBorderColor: "#eee",
        }
    ]
}

const chartDoughnutOptions = {
    plugins: {
        legend: {
            display: true,
            position: "bottom",
            labels: {
                boxWidth: 5,
                padding: 10,
                usePointStyle: true,
                pointStyle: "circle",
                font: {
                    size: 14
                }
            },
            fullSize: true,
            align: "center",
        },
        tooltip: {
            boxWidth: 15,
            bodyFont: {
                size: 14
            }
        },
        datalabels: {
            display: true,
            color: "#fff",
            align: 'buttom',
            borderRadius: 3,
            font: {
                size: 16,
            },
            formatter: (value, ctx) => {
                let sum = 0;
                let dataArr = ctx.chart.data.datasets[0].data;
                dataArr.map(data => {
                    sum += data;
                });
                let percentage = (value * 100 / sum).toFixed(1);
                if (percentage < 5) return '';
                return percentage + '%';
            },
        },
    },
    responsive: true,
    maintainAspectRatio: false,
    layout: {
        padding: {
            top: 50,
            bottom: 50
        }
    },
    elements: {
        arc: {
            borderWidth: 2,
        }
    },
    animation: {
        duration: 1000
    }
};

const chartLineOptions = {
    scales: {
        x: {
            scaleLabel: {
                display: true,
                labelString: '날짜',
            },
            grid: {
                display: false,
            },
            ticks: {
                callback(label,index,ticks) {
                    if (index != 0 && index != ticks.length - 1){
                        return;
                    }
                    const dateLabel = this.getLabelForValue(label);
                    return `${dateLabel.slice(2, 4)}-${dateLabel.slice(5)}`;
                }
            },
        },
        // y축 옵션
        y: {
            stacked: false, // 쌓임
            display: true, // y 축 show
            ticks: {
                stepSize: 10, // 증가범위
                beginAtZero: true,
                min: 0, // 최소범위
                padding: 10, // 오른쪽 간격
            },
            grid: {
                display: false,
            },
        },
    },
    plugins: {
        datalabels: {
            display: false,
        },
        legend: {
            display: false,
        }
    },
    responsive: false,
}


fetch(`/api/admin/meeting/category`)
    .then(res => res.json())
    .then(data => {
        category.labels = [];
        data.categories.forEach(item => {
            category.labels.push(item.name);
            category.nums.push(item.num);
        });
        chartCategoryData.labels = category.labels;
        chartCategoryData.datasets[0].data = category.nums;
        category.isLoaded = true;
    })

fetch(`/api/admin/meeting/region`)
    .then(res => res.json())
    .then(data => {
        region.labels = [];
        data.regions.forEach(item => {
            region.labels.push(item.name);
            region.nums.push(item.num);
        });

        for (let num of region.nums) {
            region.total += num;
        }

        chartRegionData.labels = region.labels;
        chartRegionData.datasets[0].data = region.nums;
        region.isLoaded = true;
    })


fetch('/api/admin/meeting/count/latest')
    .then(res => res.json())
    .then(data => {
        meetingPerDay.labels = [];
        data.forEach(item => {
            meetingPerDay.labels.push(item.date);
            meetingPerDay.nums.push(item.count);
        });

        const dates = getDateArrayFromNow();

        let inputCount = meetingPerDay.labels.length - 1;
        for (let i = 29; i >= 0; i--) {
            const countedDate = new Date(meetingPerDay.labels[inputCount]);
            if (isTwoDateSameDay(dates[i], countedDate)) {
                inputCount--;
                continue;
            }

            meetingPerDay.labels.splice(inputCount + 1, 0, dates[i].toISOString().slice(0, 10));
            meetingPerDay.nums.splice(inputCount + 1, 0, 0);
        }


        chartMeetingPerDayData.labels = meetingPerDay.labels;
        chartMeetingPerDayData.datasets[0].data = meetingPerDay.nums;

        // 어제 생성된 모임 개수를 반영
        let daysInData = meetingPerDay.nums.length;
        overallCount.newMeetingYesterday = meetingPerDay.nums[daysInData - 2];
        meetingPerDay.isLoaded = true;
    })


fetch('/api/admin/member/count/latest')
    .then(res => res.json())
    .then(data => {
        newMemberPerDay.labels = [];
        data.forEach(item => {
            newMemberPerDay.labels.push(item.date);
            newMemberPerDay.nums.push(item.count);
        });

        const dates = getDateArrayFromNow();

        let inputCount = newMemberPerDay.labels.length - 1;
        for (let i = 29; i >= 0; i--) {
            const countedDate = new Date(newMemberPerDay.labels[inputCount]);
            if (isTwoDateSameDay(dates[i], countedDate)) {
                inputCount--;
                continue;
            }

            newMemberPerDay.labels.splice(inputCount + 1, 0, dates[i].toISOString().slice(0, 10));
            newMemberPerDay.nums.splice(inputCount + 1, 0, 0);
        }


        chartNewMemberPerDayData.labels = newMemberPerDay.labels;
        chartNewMemberPerDayData.datasets[0].data = newMemberPerDay.nums;
        let daysInData = newMemberPerDay.nums.length;
        // 어제 가입한 사용자 개수를 반영
        overallCount.newMemberYesterday = newMemberPerDay.nums[daysInData - 2];
        newMemberPerDay.isLoaded = true;
    })

fetch('/api/admin/home')
    .then(res => res.json())
    .then(data => {
        Object.keys(data).forEach(key => {
            if (key === 'memberNum'){
            //가입한 사용자 수 반영
                overallCount.totalMember = data.memberNum;
                return;
            }
            data[key].forEach(item => {
                meetingCount[key].labels.push(item.name);
                meetingCount[key].nums.push(item.count);
            })
            meetingCount[key].isLoaded = true;
        })
        let sum = 0;
        meetingCount['closedNotClosed'].nums.forEach(num => sum += num);
        overallCount.totalMeeting = sum;


    })



function getDateArrayFromNow() {
    const result = [];
    const min = 60;
    const sec = 60;
    const hour = 24;
    const millisec = 1000;
    for (let day = 0; day < 30; day++) {
        let date = new Date(Date.now() - min * sec * hour * millisec * day);
        result.unshift(date);
    }
    return result;
}

function isTwoDateSameDay(date1, date2) {
    if (date1.getFullYear() === date2.getFullYear() &&
        date1.getMonth() === date2.getMonth() &&
        date1.getDate() === date2.getDate()) {
        return true;
    }
    return false;
}



const result = getDateArrayFromNow();
console.log(result);

</script>

<style scoped>
.admin-home{
    width:90%;
}

.overall-count-container {
    height: fit-content;
    margin-top:20px;
    background-color: #00000011;
    border-radius: 20px;
    display:flex;
    width: 90%;
}

.overall-count{
    display:flex;
    flex-direction: column;
    row-gap:30px;
    padding: 20px 30px;
}

.overall-count .add-deco-img-left{
    flex-direction: column;
}

.overall-count .add-deco-img-left::before{
    width:36px;
    height:36px;
    flex: 0 0 36px;
    background-color:#00000033;
    border-radius: 18px;
    align-self: flex-start;
    margin-bottom: 5px;
    background-size: 80%;
    background-position: center center;
}

.overall-count > label{
    font-size: 24px;
    font-weight: 600;
}

.overall-count > span{
    font-size : 18px;
    font-weight: 600;
}


.doughnut-chart-container {
    display: flex;
    flex-wrap: wrap;
    max-width: 100%;
    row-gap: 20px;
    margin-top: 20px;
    column-gap: 20px;
}

.line-chart,
.doughnut-chart {
    display: inline-flex;
    flex-direction: column;
    align-items: center;
    border-radius: 20px;
    background-color: #0000000a;
    flex: 0 1 400px;
}

.doughnut-chart {
    width: 400px;
    height: 450px;
}

.doughnut-chart>span,
.line-chart>span {
    margin-top: 20px;
    padding: 0px 30px;
    align-self: flex-start;
    font-size: 20px;
    font-weight: 600;
}

.line-chart-container {
    display: flex;
    column-gap: 20px;
}


.line-chart > *:not(span) {
    width: 600px;
    height: 300px;
}
</style>