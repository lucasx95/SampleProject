import { BaseEntity } from './../../shared';

export class SampleOneToOne implements BaseEntity {
    constructor(
        public id?: number,
        public sampleOne?: string,
    ) {
    }
}
