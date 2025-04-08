import { z } from 'zod';

const baseEventSchema = z.object({
  title: z.string().min(1, 'Title is required'),
  description: z.string().optional(),
  startTime: z.string(),
  endTime: z.string(),
  location: z.string().optional()
});

export const eventSchema = baseEventSchema.refine(
  (data) => {
    const start = new Date(data.startTime);
    const end = new Date(data.endTime);
    return start < end;
  },
  {
    message: 'End time must be after start time',
    path: ['endTime']
  }
);
