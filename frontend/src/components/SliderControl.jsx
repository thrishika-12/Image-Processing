function SliderControl({
  label,
  min,
  max,
  step,
  value,
  setValue
}) {
  return (
    <div className="slider-control">

      <label>
        {label}: {value}
      </label>

      <input
        type="range"
        min={min}
        max={max}
        step={step}
        value={value}
        onChange={(e) =>
          setValue(e.target.value)
        }
      />

    </div>
  );
}

export default SliderControl;