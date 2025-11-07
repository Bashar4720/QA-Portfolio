
const API_BASE = "http://localhost:8080";


const planForm = document.getElementById("planForm");
const planName = document.getElementById("planName");
const planDesc = document.getElementById("planDesc");
const planDiff = document.getElementById("planDiff");
const planList = document.getElementById("planList");

const traineeForm = document.getElementById("traineeForm");
const traineeName = document.getElementById("traineeName");
const traineeEmail = document.getElementById("traineeEmail");
const traineeJoin = document.getElementById("traineeJoin");
const traineesTableBody = document.getElementById("traineeTableBody");


let plans = [];
let trainees = [];


async function fetchJSON(url, options = {}) {
  const res = await fetch(url, {
    headers: { "Content-Type": "application/json" },
    ...options,
  });

  const text = await res.text();
  let data;

  try {
    data = text ? JSON.parse(text) : null;
  } catch {
    data = text;
  }

  if (!res.ok) {
    const msg = (data && data.message) || data || `Error ${res.status}`;
    throw new Error(msg);
  }

  return data;
}

function toast(msg, type = "info") {
  const t = document.createElement("div");
  t.textContent = msg;

  t.style.position = "fixed";
  t.style.bottom = "20px";
  t.style.left = "20px";
  t.style.zIndex = "9999";
  t.style.padding = "10px 14px";
  t.style.borderRadius = "10px";
  t.style.color = "#fff";
  t.style.backdropFilter = "blur(8px)";
  t.style.fontSize = "0.95rem";

  t.style.background =
    type === "error"
      ? "linear-gradient(135deg,#ef4444,#f97316)"
      : type === "success"
      ? "linear-gradient(135deg,#22c55e,#06b6d4)"
      : "linear-gradient(135deg,#334155,#0f172a)";

  document.body.appendChild(t);

  setTimeout(() => t.remove(), 2500);
}

function setBtnLoading(btn, isLoading) {
  if (!btn) return;

  btn.disabled = isLoading;

  if (isLoading) {
    btn.dataset.oldText = btn.textContent;
    btn.textContent = "...";
  } else if (btn.dataset.oldText) {
    btn.textContent = btn.dataset.oldText;
    delete btn.dataset.oldText;
  }
}

function escapeHtml(s) {
  if (s == null) return "";
  return String(s)
    .replaceAll("&", "&amp;")
    .replaceAll("<", "&lt;")
    .replaceAll(">", "&gt;")
    .replaceAll('"', "&quot;")
    .replaceAll("'", "&#39;");
}

// ==============================
// Initial Load
// ==============================
window.addEventListener("DOMContentLoaded", async () => {
  try {
    await Promise.all([loadPlans(), loadTrainees()]);
  } catch (e) {
    toast(`Failed to load: ${e.message}`, "error");
  }
});

async function loadPlans() {
  plans = await fetchJSON(`${API_BASE}/api/plans`);
  renderPlans();
}

function renderPlans() {
  planList.innerHTML = "";

  if (!plans.length) {
    const li = document.createElement("li");
    li.textContent = "No workout plans available.";
    planList.appendChild(li);
    return;
  }

  plans.forEach((p) => {
    const li = document.createElement("li");
    li.innerHTML = `
      <div style="display:flex;flex-direction:column;gap:4px;">
        <strong>${escapeHtml(p.name)}</strong>
        <span>${escapeHtml(p.description)}</span>
      </div>
      <span class="badge">Difficulty: ${p.difficulty}</span>
    `;
    planList.appendChild(li);
  });
}

async function loadTrainees() {
  trainees = await fetchJSON(`${API_BASE}/api/trainees`);
  renderTrainees();
}

function renderTrainees() {
  traineesTableBody.innerHTML = "";

  if (!trainees.length) {
    const tr = document.createElement("tr");
    const td = document.createElement("td");
    td.colSpan = 6;
    td.textContent = "No trainees found.";
    tr.appendChild(td);
    traineesTableBody.appendChild(tr);
    return;
  }

  trainees.forEach((t) => {
    const tr = document.createElement("tr");

    tr.innerHTML = `
      <td data-label="ID">${t.id}</td>
      <td data-label="Name">${escapeHtml(t.name)}</td>
      <td data-label="Email">${escapeHtml(t.email)}</td>
      <td data-label="Join Date">${t.joinDate ?? "-"}</td>
      <td data-label="Active Plan">
        <select class="plan-select" data-trainee="${t.id}">
          <option value="">Select plan</option>
          ${plans
            .map(
              (p) =>
                `<option value="${p.id}" ${
                  String(t.activePlanId ?? "") === String(p.id)
                    ? "selected"
                    : ""
                }>${escapeHtml(p.name)}</option>`
            )
            .join("")}
        </select>
      </td>
      <td class="actions" data-label="Actions">
        <button class="btn-assign" data-trainee="${t.id}">Assign</button>
        <button class="btn-secondary btn-remove" data-trainee="${t.id}" ${
      t.activePlanId ? "" : "disabled"
    }>Remove</button>
      </td>
    `;

    traineesTableBody.appendChild(tr);
  });
}

planForm.addEventListener("submit", async (e) => {
  e.preventDefault();
  const btn = document.getElementById("addPlanBtn");

  const body = {
    name: planName.value.trim(),
    description: planDesc.value.trim(),
    difficulty: Number(planDiff.value),
  };

  if (!body.name || !body.description || !body.difficulty) {
    toast("Please fill all plan fields.", "error");
    return;
  }

  try {
    setBtnLoading(btn, true);
    await fetchJSON(`${API_BASE}/api/plans`, {
      method: "POST",
      body: JSON.stringify(body),
    });
    planForm.reset();
    await loadPlans();
    toast("Plan added successfully ✅", "success");
  } catch (e2) {
    toast(`Failed to add plan: ${e2.message}`, "error");
  } finally {
    setBtnLoading(btn, false);
  }
});

traineeForm.addEventListener("submit", async (e) => {
  e.preventDefault();
  const btn = document.getElementById("addTraineeBtn");

  const body = {
    name: traineeName.value.trim(),
    email: traineeEmail.value.trim(),
    joinDate: traineeJoin.value,
  };

  if (!body.name || !body.email || !body.joinDate) {
    toast("Please fill all trainee fields.", "error");
    return;
  }

  try {
    setBtnLoading(btn, true);
    await fetchJSON(`${API_BASE}/api/trainees`, {
      method: "POST",
      body: JSON.stringify(body),
    });
    traineeForm.reset();
    await loadTrainees();
    toast("Trainee added successfully ✅", "success");
  } catch (e2) {
    toast(`Failed to add trainee: ${e2.message}`, "error");
  } finally {
    setBtnLoading(btn, false);
  }
});

traineesTableBody.addEventListener("click", async (e) => {
  const assignBtn = e.target.closest(".btn-assign");
  const removeBtn = e.target.closest(".btn-remove");

  if (assignBtn) {
    const traineeId = assignBtn.dataset.trainee;
    const select = document.querySelector(
      `select.plan-select[data-trainee="${traineeId}"]`
    );
    const planId = select.value;

    if (!planId) {
      toast("Select a plan first.", "error");
      return;
    }

    try {
      setBtnLoading(assignBtn, true);
      await fetchJSON(
        `${API_BASE}/api/trainees/${traineeId}/assign-plan/${planId}`,
        { method: "PUT" }
      );
      await loadTrainees();
      toast("Plan assigned ✅", "success");
    } catch (err) {
      toast(`Failed to assign: ${err.message}`, "error");
    } finally {
      setBtnLoading(assignBtn, false);
    }
  }

  if (removeBtn) {
    const traineeId = removeBtn.dataset.trainee;

    try {
      setBtnLoading(removeBtn, true);
      await fetchJSON(`${API_BASE}/api/trainees/${traineeId}/remove-plan`, {
        method: "PUT",
      });
      await loadTrainees();
      toast("Plan removed ✅", "success");
    } catch (err) {
      toast(`Failed to remove: ${err.message}`, "error");
    } finally {
      setBtnLoading(removeBtn, false);
    }
  }
});
