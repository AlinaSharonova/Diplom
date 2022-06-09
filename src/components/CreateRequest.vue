<template>
  <div class="body">
    <div class="left">
      <div class="left-header part-header">Фильтры</div>
      <v-date-picker style="left: 15%" v-model="date"></v-date-picker>
      <v-select :value="time" :items="items" style="width: 80%; left: 10%; padding-top: 10%"></v-select>
    </div>
    <div class="right">
      <div class="header">
        <div class="part-header">Аудитории</div>
      </div>
      <v-data-table class="table"
                    disable-sort
                    item-key="auditory"
                    hide-default-footer
                    :headers="headers"
                    :items="data">
        <template v-slot:item.auditory="{ item }">
          {{ item.number }}
        </template>
        <template v-slot:item.floor="{ item }">
          {{ item.floor ? 0 : 10 }}
        </template>
        <template v-slot:item.capacity="{ item }">
          {{ item.capacity }}
        </template>
        <template v-slot:item.action="{ item }">
          <v-btn v-on:click="book(item)">Забронировать</v-btn>
        </template>
      </v-data-table>
    </div>
  </div>
</template>

<script>
import {VDataTable} from "vuetify/lib/components";
import BookingForm from "@/components/BookingForm";

const header = [
  {
    text: 'Аудитория',
    value: 'auditory',
    align: 'center',
    width: '300px'
  },
  {
    text: 'Этаж',
    value: 'floor',
    align: 'center',
    width: '300px'
  },
  {
    text: 'Вместимость',
    value: 'capacity',
    align: 'center',
    width: '300px'
  },
  {
    text: 'Действие',
    value: 'action',
    align: 'center',
    width: '300px'
  }
]
export default {
  name: "CreateRequest",
  data() {
    return {
      headers: header,
      date: '2022-06-08',
      items: [
        '8:30 - 10:00',
        '10:10 - 11:40',
        '11:50 - 13:20',
        '14:00 - 15:30',
        '15:40 - 17:10',
        '17:20 - 19:50',
        '20:00 - 21:30'
      ],
      time: '8:30 - 10:00',
      data: []
    }
  },
  components: {
    VDataTable,
  },
  methods: {
    createRequest() {
      this.$router.push('/create')
    },
    book(auditory) {
      const self = this;
      this.$dialog.show(BookingForm, {date: self.date, time: self.time, auditory: auditory}).then(()=> {})
    }
  },
  mounted() {
    this.http.get('/auditories/').then(data => {
      this.data = data
    })
  }
}
</script>

<style scoped>
.header {
  display: grid;
  grid-template-columns: 7fr 1fr;
  padding: 1% 2% 3%;
}

.left-header {
  text-align: center;
  padding-bottom: 5%;
}

.part-header {
  color: #4B7EFF;
  font-size: 40px;
  text-decoration: underline #4B7EFF;
}


.body {
  display: grid;
  grid-template-columns: 1fr 3fr;
  grid-gap: 2%;
  z-index: 1;
  width: 90%;
  height: 90%;
  position: absolute;
  top: 5%;
  left: 5%;
}

.left {
  border: 3px #4B7EFF solid;
}

.right {
  border: 3px #4B7EFF solid;
}

.table >>> th {
  font-size: 28px !important;
}

.table >>> td {
  font-size: 28px !important;
  text-align: center;
}
</style>