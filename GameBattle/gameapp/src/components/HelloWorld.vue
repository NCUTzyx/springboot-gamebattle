<template>
  <div>
    Hello World!
  </div>
</template>

<script>
import $ from 'jquery';
export default {
  name: 'HelloWorld',

  setup(){
    let data = {
        buyer: "ncu12345",
        goods: "goods123",
        counts: 3,
      };
    // add
    $.ajax({
      url: "http://localhost:3000/buyerInfo/addToCart",
      type: "POST",
      data: JSON.stringify(data),
      contentType:"application/json",
      success(resp){
        console.log(resp.msg);
      },
    });

    //remove
    // $.ajax({
    //   url: "http://localhost:3000/buyerInfo/removeFromCart",
    //   type: "POST",
    //   data:{
    //     id: "9uUhEO66WK"
    //   },
    //   success(resp){
    //     console.log(resp.message);
    //   },
    // });

    //modify
    // $.ajax({
    //   url: "http://localhost:3000/buyerInfo/modifyAmount",
    //   type: "POST",
    //   data:{
    //     id: "yfsYbhycZN",
    //     goods: "goods123",
    //     counts: "19"
    //   },
    //   success(resp){
    //     console.log(resp.message)
    //   },
    // });

    // getList
    // const buyer = "ncu12345"
    // $.ajax({
    //   url: "http://localhost:3000/buyerInfo/getMyCart/" + buyer,
    //   type: "GET",
    //   success(resp){
    //     console.log(resp)
    //   },
    // });

    //clear
    // $.ajax({
    //   url: "http://localhost:3000/buyerInfo/clearMyCart",
    //   type: "POST",
    //   data:{
    //     buyer: "ncu12345"
    //   },
    //   success(resp){
    //     console.log(resp.message)
    //   },
    // });

  }
}
</script>
<style scoped>

</style>
