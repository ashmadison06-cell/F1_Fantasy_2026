//alert("JS is connected!");
const BUDGET = 100;
let remainingBudget = BUDGET;
let selectedDrivers = [];

const drivers = [
    { name: "Max Verstappen", cost: 37, points: 510 },
    { name: "Lando Norris", cost: 35, points: 485 },
    { name: "Charles Leclerc", cost: 33, points: 460 },
    { name: "Oscar Piastri", cost: 32, points: 445 },
    { name: "Lewis Hamilton", cost: 31, points: 430 },
    { name: "George Russell", cost: 27, points: 395 },
    { name: "Carlos Sainz", cost: 26, points: 380 },
    { name: "Fernando Alonso", cost: 24, points: 355 },
    { name: "Sergio Perez", cost: 23, points: 340 },
    { name: "Pierre Gasly", cost: 20, points: 315 },
    { name: "Kimi Antonelli", cost: 19, points: 300 },
    { name: "Alex Albon", cost: 18, points: 285 },
    { name: "Esteban Ocon", cost: 17, points: 270 },
    { name: "Lance Stroll", cost: 16, points: 255 },
    { name: "Nico Hulkenberg", cost: 14, points: 235 },
    { name: "Liam Lawson", cost: 13, points: 220 },
    { name: "Isack Hadjar", cost: 12, points: 210 },
    { name: "Ollie Bearman", cost: 11, points: 200 },
    { name: "Franco Colapinto", cost: 10, points: 190 },
    { name: "Gabriel Bortoleto", cost: 9, points: 180 },
    { name: "Arvid Lindblad", cost: 8, points: 170 },
    { name: "Valtteri Bottas", cost: 7, points: 160 }
];

const driverContainer = document.getElementById("drivers");
const budgetDisplay = document.getElementById("budget");
const finishBtn = document.getElementById("finishBtn");
const resultDiv = document.getElementById("result");

drivers.forEach(driver => {
    const card = document.createElement("div");
    card.classList.add("card");
    card.innerHTML = `<h3>${driver.name}</h3>
                      <p>Cost: $${driver.cost}M</p>
                      <p>Captain Cost: ${Math.ceil(driver.cost * 0.25)}M</p>
                      <button class="captain-btn">Make Captain</button>
                    `;
const captainBtn = card.querySelector(".captain-btn");
captainBtn.addEventListener("click", () => chooseCaptain(driver));
    
    card.addEventListener("click", () => {
        if (selectedDrivers.includes(driver)) {
            selectedDrivers = selectedDrivers.filter(d => d !== driver);
            remainingBudget += driver.cost;
            card.classList.remove("selected");
        } else {
            if (selectedDrivers.length >= 5) {
                alert("You can only select 5 drivers.");
                return;
            }
            if (driver.cost > remainingBudget) {
                alert("Not enough budget!");
                return;
            }
            selectedDrivers.push(driver);
            remainingBudget -= driver.cost;
            card.classList.add("selected");
        }

        updateUI();
    });

    driverContainer.appendChild(card);
});

function updateUI() {
    budgetDisplay.textContent = `Budget: $${remainingBudget}M`;
    finishBtn.disabled = selectedDrivers.length !== 5;
}

function chooseCaptain(driver) {
    const captain = driver; // set the chosen driver
    //  calculate captain cost and see if budget is enough
    const captainCost = Math.ceil(driver.cost * 0.25);
    if (captainCost > remainingBudget) {
        alert("Not enough budget to make this driver captain!");
        return;
    }
    remainingBudget -= captainCost;
    budgetDisplay.textContent = `Budget: $${remainingBudget}M`;
    
    //Highlight captain card    
    document.querySelectorAll(".card").forEach(c => c.classList.remove("captain"));
    card.classList.add("captain");
    
    // Show final team
    const finalTeam = [captain, ...selectedDrivers.filter(d => d !== captain)];
    resultDiv.classList.remove("hidden");
    resultDiv.innerHTML = `
        <h2>🏁 FINAL TEAM 🏁</h2>
        ${finalTeam.map(d =>
            `<p>${d.name}${d === captain ? " (Captain)" : ""}</p>`
        ).join("")}  
        <p>Budget Remaining: $${remainingBudget}M</p>
    `;

    finishBtn.disabled = true;
}

    

    

    
    let totalPoints = 0;
    selectedDrivers.forEach(d => {
        totalPoints += d === captain ? d.points * 2 : d.points;
    });

    resultDiv.classList.remove("hidden");
    
    const finalTeam = [
    captain, // always first
    ...selectedDrivers.filter(d => d !== captain) // the rest
    ];
    
    resultDiv.innerHTML = `
        <h2>🏁 FINAL TEAM 🏁</h2>
        ${finalTeam.map(d =>
            `<p>${d.name}${d === captain ? " (Captain)" : ""}</p>`
        ).join("")}  
        <p>Budget Remaining: $${remainingBudget}M</p>
    `;

    finishBtn.disabled = true;
});
