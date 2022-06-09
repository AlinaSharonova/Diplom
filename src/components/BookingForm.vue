<template>
  <v-card data-app class="overflow-auto">
    <v-card-title>
      Заявка на бронирование
    </v-card-title>
    <v-card-text>
      <v-row>
        <v-col>
          Пользователь:
        </v-col>
        <v-col>
          {{ user }}
        </v-col>
      </v-row>
      <v-row>
        <v-col>Дата:</v-col>
        <v-col>{{ this.date }}</v-col>
      </v-row>
      <v-row>
        <v-col>Время:</v-col>
        <v-col>{{ this.time }}</v-col>
      </v-row>
      <v-row>
        <v-col>Период:</v-col>
        <v-col>
          <v-select :items="periods" v-model="period"></v-select>
        </v-col>
      </v-row>
      <v-row>
        <v-col>Аудитория</v-col>
        <v-col>{{ this.auditory.number }}</v-col>
      </v-row>
      <v-row>
        <v-col>Требования</v-col>
        <v-col>
          <v-select multiple v-model="this.requirement" item-value="id" item-text="type"
                    :items="requirementList"></v-select>
        </v-col>
      </v-row>
      <v-row>
        <v-col>Цель</v-col>
        <v-col>
          <v-textarea v-model="purpose"/>
        </v-col>
      </v-row>
      <v-row justify="end" style="margin: 1%">
        <v-btn style="margin-right: 10px;color: #4B7EFF">
          Отменить
        </v-btn>
        <v-btn color="#4B7EFF" style="color: white" v-on:click="save">
          Забронировать
        </v-btn>
      </v-row>
    </v-card-text>
  </v-card>
</template>

<script>

const periods = [
  "WEEKLY",
  "ONE_IN_TWO_WEEKS",
  "MONTHLY",
  "ONCE"
]
export default {
  name: "BookingForm",
  data() {
    return {
      user: "Шаронова Алина Дмитриевна",
      purpose: "",
      period: "",
      periods: periods,
      requirement: [],
      requirementList: []
    }
  },
  mounted() {
    this.http.get('/requirements').then(data => {
      this.requirementList = data
    })
  },
  methods: {
    save() {
      const self = this
      let request = {
        time: self.resolveTime(this.time),
        name: "Lesson",
        period: this.period,
        startDate: this.date,
        auditory: this.auditory.id,
        requirements: this.requirement,
        purpose: this.purpose,
        email: this.user
      }
      this.http.post("/requests/save", null, request).then(response => {
        console.log(response);
        self.$emit('close');
      })

    },
    resolveTime(time) {
      switch (time) {
        case "8:30 - 10:00":
          return "FIRST"
        case "10:10 - 11:40":
          return "SECOND"
        case "11:50 - 13:20":
          return "THIRD"
        case "14:00 - 15:30":
          return "FOURTH"
        case "15:40 - 17:10":
          return "FIFTH"
        case "17:20 - 19:50":
          return "SIXTH"
        case "20:00 - 21:30":
          return "SEVENTH"
      }
    }
  },
  props: [
    'date',
    'time',
    'auditory'
  ]
}
</script>

<style scoped>


</style>