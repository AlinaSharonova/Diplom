<template>
  <div class="body">
    <div class="left">
      <div class="left-header part-header">Ваши данные</div>
      <div class="fio">Фамилия Имя Отчество</div>
      <div class="fio">Шаронова Алина Дмитриевна</div>
      <div class="fio">Должность</div>
      <div class="fio">Студент</div>
      <div class="fio">Контактные данные</div>
      <div class="fio">pochta@kpfu.ru<br> 89876543210</div>
    </div>
    <div class="right">
      <div class="header">
        <div class="part-header">Ваши заявки</div>
        <v-btn x-large style="color: white" color="#18A0FB" v-on:click="createRequest()">Новая заявка</v-btn>
      </div>
      <v-data-table class="table"
                    disable-sort
                    hide-default-footer
                    :headers="headers"
                    :items="data">
        <template v-slot:item.auditory="{ item }">
          {{ item.auditory.number }}
        </template>
        <template v-slot:item.date="{ item }">
          {{ item.startDate }}
        </template>
        <template v-slot:item.time="{ item }">
          {{ resolveTime(item.time) }}
        </template>
        <template v-slot:item.status="{ item }">
          <div>
            {{ resolveStatus(item.status) }}
            <v-menu v-if="admin && item.status === 'TO_APPROVE'"
                    style="background-color: white"
                    bottom
                    left>
              <template v-slot:activator="{ on, attrs }">
                <v-btn
                    dark
                    icon
                    v-bind="attrs"
                    v-on="on">
                  <v-icon color="black">mdi-dots-vertical</v-icon>
                </v-btn>
              </template>
              <div style="display: grid; align-content: flex-end; gap: 1px">
                <v-btn v-on:click="action('APPROVED', item.id)">Одобрить</v-btn>
                <v-btn v-on:click="action('DECLINED', item.id)">Отклонить</v-btn>
              </div>
            </v-menu>
          </div>
        </template>
      </v-data-table>

    </div>
  </div>
</template>

<script>
import {VDataTable} from "vuetify/lib/components";

const header = [
  {
    text: 'Аудитория',
    value: 'auditory',
    align: 'center',
    width: '300px'
  },
  {
    text: 'Дата',
    value: 'date',
    align: 'center',
    width: '300px'
  },
  {
    text: 'Время',
    value: 'time',
    align: 'center',
    width: '300px'
  },
  {
    text: 'Статус',
    value: 'status',
    align: 'center',
    width: '300px'
  }


]
export default {
  name: 'ComponentRequest',
  data() {
    return {
      headers: header,
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
    resolveTime(time) {
      switch (time) {
        case "FIRST":
          return "8:30 - 10:00"
        case "SECOND":
          return "10:10 - 11:40"
        case "THIRD":
          return "11:50 - 13:20"
        case "FOURTH":
          return "14:00 - 15:30"
        case "FIFTH":
          return "15:40 - 17:10"
        case "SIXTH":
          return "17:20 - 19:50"
        case "SEVENTH":
          return "20:00 - 21:30"
      }
    },
    resolveStatus(status) {
      switch (status) {
        case "APPROVED":
          return "Одобрено"
        case "TO_APPROVE":
          return "Отправлено"
        case "DECLINED":
          return "Отказано"
      }
    },
    action(status, id) {
      this.http.post('/requests/action',  {
        id: id,
        status: status
      })
    }
  },
  mounted() {
    this.http.get('/requests').then(data => {
      this.data = data
    })
  },
  computed: {
    admin: function () {
      return localStorage.getItem('admin') === 'true'
    }
  }
}
</script>

<style scoped>
.header {
  display: grid;
  padding-right: 2%;
  padding-left: 2%;
  padding-top: 1%;
  grid-template-columns: 7fr 1fr;
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

.fio {
  font-size: 25px;
  padding-bottom: 6%;
  position: relative;
  left: 10%;
  width: 80%;
}

.fio:nth-child(2n + 1) {
  background-color: #ECE8E8;
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