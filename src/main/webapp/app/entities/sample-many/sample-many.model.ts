import { BaseEntity } from './../../shared';

export class SampleMany implements BaseEntity {
    constructor(
        public id?: number,
        public sampleMany?: string,
        public sampleId?: number,
    ) {
    }
}
