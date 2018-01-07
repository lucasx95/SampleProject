import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { SampleMany } from './sample-many.model';
import { SampleManyPopupService } from './sample-many-popup.service';
import { SampleManyService } from './sample-many.service';
import { Sample, SampleService } from '../sample';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-sample-many-dialog',
    templateUrl: './sample-many-dialog.component.html'
})
export class SampleManyDialogComponent implements OnInit {

    sampleMany: SampleMany;
    isSaving: boolean;

    samples: Sample[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private sampleManyService: SampleManyService,
        private sampleService: SampleService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.sampleService.query()
            .subscribe((res: ResponseWrapper) => { this.samples = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.sampleMany.id !== undefined) {
            this.subscribeToSaveResponse(
                this.sampleManyService.update(this.sampleMany));
        } else {
            this.subscribeToSaveResponse(
                this.sampleManyService.create(this.sampleMany));
        }
    }

    private subscribeToSaveResponse(result: Observable<SampleMany>) {
        result.subscribe((res: SampleMany) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: SampleMany) {
        this.eventManager.broadcast({ name: 'sampleManyListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackSampleById(index: number, item: Sample) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-sample-many-popup',
    template: ''
})
export class SampleManyPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private sampleManyPopupService: SampleManyPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.sampleManyPopupService
                    .open(SampleManyDialogComponent as Component, params['id']);
            } else {
                this.sampleManyPopupService
                    .open(SampleManyDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
