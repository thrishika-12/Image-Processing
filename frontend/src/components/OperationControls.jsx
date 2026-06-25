import { useState, useEffect } from "react";
import API from "../api/imageApi";
import SliderControl from "./SliderControl";

function OperationControls({
  selectedOperation,
  imageFile,
  setProcessedPreview,
  setShapeResult
}) {

const [value, setValue] =
  useState(1);

const [direction,
  setDirection] =
  useState("clockwise");

const [flipType,
  setFlipType] =
  useState("horizontal");

useEffect(() => {

  switch (selectedOperation) {

    case "brightness":
      setValue(0);
      break;

    case "contrast":
      setValue(1);
      break;

    case "blur":
      setValue(1);
      break;

    case "sharpen":
      setValue(1);
      break;

    case "rotate":
      setValue(90);
      break;

    case "background":
      setValue(128);
      break;

    case "zoom":
      setValue(2);
      break;

    case "zoomout":
      setValue(2);
      break;

    default:
      break;
  }

}, [selectedOperation]);

  const processImage =
    async () => {

      if (
        !imageFile &&
        selectedOperation !== "layer"
      ) {
        alert(
          "Upload an image first"
        );
        return;
      }

      try {

        const formData =
          new FormData();

        let response;

        switch (
          selectedOperation
        ) {

          case "brightness":

            formData.append(
              "image",
              imageFile
            );

            formData.append(
              "brightness",
              value
            );

            response =
              await API.post(
                "/brightness",
                formData,
                {
                  responseType:
                    "blob"
                }
              );

            break;

          case "contrast":

            formData.append(
              "image",
              imageFile
            );

            formData.append(
              "contrastFactor",
              value
            );

            response =
              await API.post(
                "/contrast",
                formData,
                {
                  responseType:
                    "blob"
                }
              );

            break;

          case "blur":

            formData.append(
              "image",
              imageFile
            );

            formData.append(
              "intensity",
              value
            );

            response =
              await API.post(
                "/blur",
                formData,
                {
                  responseType:
                    "blob"
                }
              );

            break;

          case "sharpen":

            formData.append(
              "image",
              imageFile
            );

            formData.append(
              "intensity",
              value
            );

            response =
              await API.post(
                "/sharpen",
                formData,
                {
                  responseType:
                    "blob"
                }
              );

            break;

          case "rotate":

            formData.append(
              "image",
              imageFile
            );

            formData.append(
              "angle",
              value
            );

            formData.append(
              "direction",
              direction
            );

            response =
              await API.post(
                "/rotate",
                formData,
                {
                  responseType:
                    "blob"
                }
              );

            break;

          case "flip":

            formData.append(
              "image",
              imageFile
            );

            formData.append(
              "flipType",
              flipType
            );

            response =
              await API.post(
                "/flip",
                formData,
                {
                  responseType:
                    "blob"
                }
              );

            break;

          case "grayscale":

            formData.append(
              "image",
              imageFile
            );

            response =
              await API.post(
                "/grayscale",
                formData,
                {
                  responseType:
                    "blob"
                }
              );

            break;

          case "background":

            formData.append(
              "image",
              imageFile
            );

            formData.append(
              "threshold",
              value
            );

            response =
              await API.post(
                "/remove-background",
                formData,
                {
                  responseType:
                    "blob"
                }
              );

            break;

          case "zoom":

            formData.append(
              "image",
              imageFile
            );

            formData.append(
              "zoomFactor",
              value
            );

            response =
              await API.post(
                "/zoom",
                formData,
                {
                  responseType:
                    "blob"
                }
              );

            break;

          case "zoomout":

            formData.append(
              "image",
              imageFile
            );

            formData.append(
              "zoomFactor",
              value
            );

            response =
              await API.post(
                "/zoom-out",
                formData,
                {
                  responseType:
                    "blob"
                }
              );

            break;

          case "shape":

            formData.append(
              "image",
              imageFile
            );

            const shapeResponse =
              await API.post(
                "/detect-shape",
                formData
              );

            setShapeResult(
              shapeResponse.data
            );

            setProcessedPreview(
              URL.createObjectURL(
                imageFile
              )
            );

            return;

          default:
            return;
        }

        const imageUrl =
          URL.createObjectURL(
            response.data
          );

        setShapeResult(null);

        setProcessedPreview(
          imageUrl
        );

      } catch (error) {

        console.error(error);

        alert(
          "Operation failed"
        );
      }
    };

  return (
    <div className="operation-controls">

      {(selectedOperation ===
        "brightness") && (
        <SliderControl
          label="Brightness"
          min={-100}
          max={100}
          step={1}
          value={value}
          setValue={setValue}
        />
      )}

      {(selectedOperation ===
        "contrast") && (
        <SliderControl
          label="Contrast"
          min={1}
          max={5}
          step={0.1}
          value={value}
          setValue={setValue}
        />
      )}

      {(selectedOperation ===
        "blur") && (
        <SliderControl
          label="Blur"
          min={1}
          max={20}
          step={1}
          value={value}
          setValue={setValue}
        />
      )}

      {(selectedOperation ===
        "sharpen") && (
        <SliderControl
          label="Sharpen"
          min={1}
          max={20}
          step={1}
          value={value}
          setValue={setValue}
        />
      )}

      {(selectedOperation ===
        "rotate") && (
        <>
          <SliderControl
            label="Angle"
            min={0}
            max={360}
            step={1}
            value={value}
            setValue={setValue}
          />

          <select
            value={direction}
            onChange={(e) =>
              setDirection(
                e.target.value
              )
            }
          >
            <option value="clockwise">
              Clockwise
            </option>

            <option value="anticlockwise">
              Anti Clockwise
            </option>
          </select>
        </>
      )}

      {(selectedOperation ===
        "flip") && (
        <select
          value={flipType}
          onChange={(e) =>
            setFlipType(
              e.target.value
            )
          }
        >
          <option value="horizontal">
            Horizontal
          </option>

          <option value="vertical">
            Vertical
          </option>
        </select>
      )}

      {(selectedOperation ===
        "background") && (
        <SliderControl
          label="Threshold"
          min={0}
          max={255}
          step={1}
          value={value}
          setValue={setValue}
        />
      )}

      {selectedOperation === "zoom" && (
        <SliderControl
          label="Zoom Factor"
          min={2}
          max={5}
          step={1}
          value={value}
          setValue={setValue}
        />
      )}
     {selectedOperation === "zoomout" && (
       <SliderControl
         label="Zoom Factor"
         min={2}
         max={5}
         step={1}
         value={value}
         setValue={setValue}
       />
     )}

      <button
        className="apply-btn"
        onClick={processImage}
      >
        Apply
      </button>

    </div>
  );
}

export default OperationControls;