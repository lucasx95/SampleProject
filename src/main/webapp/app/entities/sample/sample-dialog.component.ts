import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Sample } from './sample.model';
import { SamplePopupService } from './sample-popup.service';
import { SampleService } from './sample.service';
import { SampleOneToOne, SampleOneToOneService } from '../sample-one-to-one';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-sample-dialog',
    templateUrl: './sample-dialog.component.html'
})
export class SampleDialogComponent implements OnInit {

    sample: Sample;
    isSaving: boolean;

    sampleones: SampleOneToOne[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private sampleService: SampleService,
        private sampleOneToOneService: SampleOneToOneService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.sampleOneToOneService
            .query({filter: 'sample-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.sample.sampleOneId) {
                    this.sampleones = res.json;
                } else {
                    this.sampleOneToOneService
                        .find(this.sample.sampleOneId)
                        .subscribe((subRes: SampleOneToOne) => {
                            this.sampleones = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.sample.id !== undefined) {
            this.subscribeToSaveResponse(
                this.sampleService.update(this.sample));
        } else {
            this.subscribeToSaveResponse(
                this.sampleService.create(this.sample));
        }
    }

    private subscribeToSaveResponse(result: Observable<Sample>) {
        result.subscribe((res: Sample) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Sample) {
        this.eventManager.broadcast({ name: 'sampleListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackSampleOneToOneById(index: number, item: SampleOneToOne) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-sample-popup',
    template: ''
})
export class SamplePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private samplePopupService: SamplePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.samplePopupService
                    .open(SampleDialogComponent as Component, params['id']);
            } else {
                this.samplePopupService
                    .open(SampleDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
