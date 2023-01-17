<script setup></script>

<template>
  <div class="soft-toggle-wrap">
    <label class="soft-toggle__switch">
      <input type="checkbox" />
      <span class="soft-toggle__slider"></span>
    </label>
  </div>
</template>

<style>
.soft-toggle__switch {
  --button-width: 3.5em;
  --button-height: 2em;
  --toggle-diameter: 1.5em;
  --button-toggle-offset: calc(
    (var(--button-height) - var(--toggle-diameter)) / 2
  );
  --toggle-shadow-offset: 10px;
  --toggle-wider: 3em;
  --color-grey: #cccccc;
  --color-green: #4296f4;
}

.soft-toggle__slider {
  display: inline-block;
  width: var(--button-width);
  height: var(--button-height);
  background-color: var(--color-grey);
  border-radius: calc(var(--button-height) / 2);
  position: relative;
  transition: 0.3s all ease-in-out;
}

.soft-toggle__slider::after {
  content: "";
  display: inline-block;
  width: var(--toggle-diameter);
  height: var(--toggle-diameter);
  background-color: #fff;
  border-radius: calc(var(--toggle-diameter) / 2);
  position: absolute;
  top: var(--button-toggle-offset);
  transform: translateX(var(--button-toggle-offset));
  box-shadow: var(--toggle-shadow-offset) 0
    calc(var(--toggle-shadow-offset) * 4) rgba(0, 0, 0, 0.1);
  transition: 0.3s all ease-in-out;
}

.soft-toggle__switch input[type="checkbox"]:checked + .soft-toggle__slider {
  background-color: var(--main-color);
}

.soft-toggle__switch
  input[type="checkbox"]:checked
  + .soft-toggle__slider::after {
  transform: translateX(
    calc(
      var(--button-width) - var(--toggle-diameter) - var(--button-toggle-offset)
    )
  );
  box-shadow: calc(var(--toggle-shadow-offset) * -1) 0
    calc(var(--toggle-shadow-offset) * 4) rgba(0, 0, 0, 0.1);
}

.soft-toggle__switch input[type="checkbox"] {
  display: none;
}

.soft-toggle__switch
  input[type="checkbox"]:active
  + .soft-toggle__slider::after {
  width: var(--toggle-wider);
}

.soft-toggle__switch
  input[type="checkbox"]:checked:active
  + .soft-toggle__slider::after {
  transform: translateX(
    calc(
      var(--button-width) - var(--toggle-wider) - var(--button-toggle-offset)
    )
  );
}
</style>
