const AddToCart = document.getElementsByClassName('addProduct');
const IncreaseQuantityButton = document.getElementsByClassName('increaseQuantity');
const DecreaseQuantityButton = document.getElementsByClassName('decreaseQuantity');
const CartSize = document.getElementById('badge');
const RemoveToCart = document.getElementsByClassName('removeProduct');
const OrderSummary = document.getElementById('final-value');
const DeleteButtons = document.getElementsByClassName('deleteProduct');
let cartSizeDiv = document.getElementById("cartSize").textContent;

for(let button of DeleteButtons){
    button.addEventListener("click",removeGivenProduct);
    button.addEventListener("click",updateViewAfterItemDeletion);
}

for(let button of IncreaseQuantityButton){
    button.addEventListener("click",addItemToCart);
    button.addEventListener("click", increaseProductQuantity);
    button.addEventListener("click",increaseCartSize);
}
for(let button of DecreaseQuantityButton){
    button.addEventListener("click", decreaseProductQuantityInCart);
    button.addEventListener("click", decreaseProductQuantity);
    button.addEventListener("click",decreaseCartSize)
}
for(let cart of AddToCart){
    cart.addEventListener("click", addItemToCart);
    cart.addEventListener("click", increaseCartSize);
}
for(let cart of RemoveToCart){
    cart.addEventListener("click", decreaseProductQuantityInCart);
}

async function addItemToCart(event){
    return await fetch('/add?prod_id=' +event.target.dataset.id,{method:"POST"});
}
async function removeGivenProduct(event){
    return await fetch('/removeAll?name='+event.target.dataset.name, {method:"DELETE"});
}
async function decreaseProductQuantityInCart(event){
    return await fetch('/decreaseProductQuantity?prod_id=' +event.target.dataset.id,{method:"DELETE"});
}

function increaseProductQuantity(event){
    let productQuantityDiv = document.getElementById("count_"+event.target.dataset.id);
    let productValueDiv = document.getElementById("value_"+event.target.dataset.id);
    let productQuantity = parseInt(productQuantityDiv.textContent);
    let productValue = parseFloat(productValueDiv.textContent);
    productValueDiv.textContent=(productValue+productValue/productQuantity).toFixed(1).toString() + ' USD';
    productQuantityDiv.textContent=(productQuantity+1).toString();
    OrderSummary.textContent = (parseFloat(OrderSummary.textContent)+productValue/productQuantity).toFixed(1).toString() + " USD";
}
function updateViewAfterItemDeletion(event){
    let productValueDiv = document.getElementById("value_"+event.target.dataset.id);
    let productValue = parseFloat(productValueDiv.textContent);
    OrderSummary.textContent = (parseFloat(OrderSummary.textContent)-productValue).toFixed(1).toString();
    let text = CartSize.textContent;
    let number = parseInt(text);
    let string1 = document.getElementById("count_"+event.target.dataset.id).textContent;
    let numberOfProductsToDelete = parseInt(string1);
    CartSize.textContent = (number - numberOfProductsToDelete).toString();
    sessionStorage.setItem("cartSize",CartSize.textContent = (number - numberOfProductsToDelete).toString());
    deleteProductFromCart(event);
    if((parseInt(OrderSummary.textContent) <=0)){
        emptyCart();
    }


}
function emptyCart() {
    document.getElementById("cartWithProducts").remove();
    document.getElementById("orderValue").remove();
    document.getElementById("container").innerText = "Pusty Koszyk";
}

function decreaseProductQuantity(event){
    let productQuantityDiv = document.getElementById("count_"+event.target.dataset.id);
    let productValueDiv = document.getElementById("value_"+event.target.dataset.id);
    let productQuantity = parseInt(productQuantityDiv.textContent);
    let productValue = parseFloat(productValueDiv.textContent).toFixed(1);
    if(productQuantity-1 <=0){
        deleteProductFromCart(event);
        if(parseFloat(OrderSummary.textContent) - productValue / productQuantity <=0){
            emptyCart();
        }
    }else {
        productValueDiv.textContent=(productValue-productValue/productQuantity).toFixed(1).toString() + " USD";
        productQuantityDiv.textContent = (productQuantity - 1).toString();
    }
    OrderSummary.textContent = (parseFloat(OrderSummary.textContent) - productValue / productQuantity).toFixed(1).toString() + " USD";
}
function deleteProductFromCart(event){
    let divToDelete = document.getElementById("product_"+event.target.dataset.id);
    divToDelete.remove();
}

function increaseCartSize() {
    let text = CartSize.textContent;
    let number = parseInt(text);
    CartSize.textContent = (number + 1).toString();
    cartSizeDiv = (number+1).toString();
    sessionStorage.setItem("cartSize",CartSize.textContent = (number + 1).toString());
}
function decreaseCartSize() {
    let text = CartSize.textContent;
    let number = parseInt(text);
    CartSize.textContent = (number - 1).toString();
    cartSizeDiv = (number-1).toString();
    sessionStorage.setItem("cartSize",CartSize.textContent = (number - 1).toString());
}
function loadCartSize(){
    if(cartSizeDiv === ""){
        CartSize.textContent = "0";
    }else{
        CartSize.textContent = cartSizeDiv;
    }
    // console.log(cartSizeDiv);
    // console.log("DUPAAAA");
    // if(sessionStorage.getItem("cartSize")===null){
    //     CartSize.textContent = "20";
    // }else {
    //     CartSize.textContent = sessionStorage.getItem("cartSize");
    // }
}
window.onscroll = function() {myFunction()};

// Get the navbar
let navbar = document.getElementById("navbar");

// Get the offset position of the navbar
let sticky = navbar.offsetTop;

// Add the sticky class to the navbar when you reach its scroll position. Remove "sticky" when you leave the scroll position
function myFunction() {
    if (window.pageYOffset >= sticky) {
        navbar.classList.add("sticky")
    } else {
        navbar.classList.remove("sticky");
    }
}


